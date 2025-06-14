package com.am.MyBankPlus.service.impl;

import com.am.MyBankPlus.dto.Role;
import com.am.MyBankPlus.dto.Status;
import com.am.MyBankPlus.model.ClientScore;
import com.am.MyBankPlus.model.User;
import com.am.MyBankPlus.repository.UserRepository;
import com.am.MyBankPlus.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final GiniBankMetrics giniBankMetrics;

    private BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    public String calculateGiniCoefficient() {
        List<ClientScore> j = new ArrayList<>();
        for (User a : getAll()) {
            if (a.getCard().getCreditBalance() == a.getCard().getCreditLimit()) {
                j.add(new ClientScore(a.getCard().getBalance(), true));
                userRepository.getReferenceById(a.getId()).setStatus(Status.GOOD_CLIENT);
            } else {
                j.add(new ClientScore(-a.getCard().getCreditBalance(), false));
                System.out.println(-a.getCard().getCreditBalance());
                userRepository.getReferenceById(a.getId()).setStatus(Status.BAD_CLIENT);
            }
            userRepository.save(a);
        }
        double giniMetric = giniBankMetrics.calculateGini(j);
        return String.format("%.2f", giniMetric);
    }


    @Override
    public User getUserAut(Authentication authentication) {
        log.info("Returning details for user: {}", authentication.getName());
        Optional<User> optional = userRepository.findByEmail(authentication.getName());
        ArrayList<User> res = new ArrayList<>();
        optional.ifPresent(res::add);
        return res.get(authentication.getName().indexOf(authentication.getName()));
    }


    @Override
    public void getUserById(Long id, Model model) {
        log.info("Getting user data by id: {}", id);
        Optional<User> u = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        u.ifPresent(res::add);
        model.addAttribute("user", res);
    }

    @Override
    public User getByPhoneNumber(String phone) {

        Optional<User> optional = userRepository.findByPhoneNumber(phone);
        ArrayList<User> res = new ArrayList<>();
        optional.ifPresent(res::add);
        if (res.stream().filter(p -> p.getPhoneNumber().equals(phone)).findFirst().isEmpty()) {
            log.info("User with this phone number not found: {}", phone);
            return null;
        } else {
            log.info("User found by phone successfully: {}", phone);
            return res.stream().filter(p -> p.getPhoneNumber().equals(phone)).findFirst().orElseThrow();
        }
    }

    @Override
    public void userDelete(long id, Model model) {
        log.debug("Removing user by id: {}", id);
        User user = userRepository.findById(id).orElseThrow();
        if (userRepository.findAll().size() > 1 && user.getRole() == Role.USER) {
            userRepository.delete(user);
            log.info("User removed successfully");
        } else {
            userRepository.findById(id);
            log.info("The user was not deleted");
        }
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User updatePassword(Authentication authentication, String newPassword, String repeatPassword) {
        User user = getUserAut(authentication);
        if (!newPassword.equals(repeatPassword)) {
            log.info("Error updating user password");
            return null;
        } else {
            user.setPassword(encoder().encode(newPassword));
            log.info("User update password successfully");
            return userRepository.save(user);
        }
    }
}
