package com.am.MyBankPlus.service.impl;

import com.am.MyBankPlus.debit.DebitCard;
import com.am.MyBankPlus.dto.Register;
import com.am.MyBankPlus.dto.Role;
import com.am.MyBankPlus.model.Card;
import com.am.MyBankPlus.model.User;
import com.am.MyBankPlus.model.UserDetailsInfo;
import com.am.MyBankPlus.repository.DebitRepository;
import com.am.MyBankPlus.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final DebitRepository debitRepository;

    private BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User with email '" + username + "' not found"));
        return UserDetailsInfo.build(user);
    }

    public User saveUser(Register register) {
        log.debug("Adding user");
        if (userRepository.findByEmail(register.getEmail()).isPresent() || userRepository.findByPhoneNumber(register.getPhoneNumber()).isPresent()) {
            log.info("The user was not added");
            return null;
        } else {


            User user = new User();
            user.setFirstName(register.getFirstName());
            user.setLastName(register.getLastName());
            user.setMiddleName(register.getMiddleName());
            user.setPhoneNumber(register.getPhoneNumber());
            user.setDateOfBirth(register.getDateOfBirth());
            user.setEmail(register.getEmail());
            user.setPassword(encoder().encode(register.getPassword()));
            changeRole(register);
            user.setRole(register.getRole());

            userRepository.save(user);
            Card card = new Card();
            user.setCard(new DebitCard(card).getCard());
            debitRepository.save(card);
            changeRole(register);
            log.info("The user has been added");
            return userRepository.save(user);
        }
    }

    public static void changeRole(Register register) {
        String firstFour = register.getEmail().substring(0, 5);
        if (firstFour.equals("admin")) {
            register.setRole(Role.ADMIN);
        } else {
            register.setRole(Role.USER);
        }
    }
}
