package com.am.MyBankPlus.service.impl;

import com.am.MyBankPlus.credit.CreditCard;
import com.am.MyBankPlus.model.BankCard;
import com.am.MyBankPlus.model.Card;
import com.am.MyBankPlus.model.User;
import com.am.MyBankPlus.repository.CreditRepository;
import com.am.MyBankPlus.service.CreditService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditRepository repository;

    private final UserServiceImpl userService;


    @Override
    public Card addBalance(double amount, Authentication authentication) {
        User user = userService.getUserAut(authentication);
        CreditCard creditCard = new CreditCard(user.getCard());
        creditCard.addBalance(amount);
        user.getCard().setAllBalance(creditCard.checkAllBalance());
        userService.calculateGiniCoefficient();
        return repository.save(user.getCard());
    }

    @Override
    public Card pay(double amount, Authentication authentication) {
        User user = userService.getUserAut(authentication);
        BankCard creditCard = new CreditCard(user.getCard());
        if (user.getCard().getBalance() + user.getCard().getCreditBalance() < amount) {
            return null;
        } else {
            creditCard.pay(amount);
            userService.calculateGiniCoefficient();
            return repository.save(user.getCard());
        }
    }

    @Override
    public void sendByPhone(double amount, String phoneNumber, Authentication authentication) {
        User user = userService.getByPhoneNumber(phoneNumber);
        BankCard creditCard = new CreditCard(user.getCard());
        creditCard.addBalance(amount);
        user.getCard().setAllBalance(creditCard.checkAllBalance());
        userService.calculateGiniCoefficient();
        repository.save(user.getCard());

    }

    @Override
    public List<Card> getAll() {
        return repository.findAll();
    }
}
