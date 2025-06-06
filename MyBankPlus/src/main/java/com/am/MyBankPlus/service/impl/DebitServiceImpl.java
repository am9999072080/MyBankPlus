package com.am.MyBankPlus.service.impl;

import com.am.MyBankPlus.debit.DebitCard;
import com.am.MyBankPlus.model.BankCard;
import com.am.MyBankPlus.model.Card;
import com.am.MyBankPlus.model.User;
import com.am.MyBankPlus.repository.DebitRepository;
import com.am.MyBankPlus.service.DebitService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DebitServiceImpl implements DebitService {

    private final DebitRepository repository;

    private final UserServiceImpl userService;

    @Override
    public Card addBalance(double amount, Authentication authentication) {
        User user = userService.getUserAut(authentication);
        BankCard debitCard = new DebitCard(user.getCard());
        debitCard.addBalance(amount);
        user.getCard().setAllBalance(debitCard.checkAllBalance());
        return repository.save(user.getCard());
    }

    @Override
    public Card pay(double amount, Authentication authentication) {
        User user = userService.getUserAut(authentication);
        DebitCard debitCard = new DebitCard(user.getCard());
        if (user.getCard().getBalance() < amount) {
            return null;
        } else {
            debitCard.pay(amount);
            return repository.save(user.getCard());
        }
    }

    @Override
    public void sendByPhone(double amount, String phoneNumber, Authentication authentication) {
        User user = userService.getByPhoneNumber(phoneNumber);
        BankCard debitCard = new DebitCard(user.getCard());
        debitCard.addBalance(amount);
        user.getCard().setAllBalance(debitCard.checkAllBalance());
        repository.save(user.getCard());
    }

    @Override
    public List<Card> getAll() {
        return repository.findAll();
    }
}
