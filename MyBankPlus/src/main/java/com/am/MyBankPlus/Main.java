package com.am.MyBankPlus;

import com.am.MyBankPlus.credit.CreditCard;
import com.am.MyBankPlus.debit.DebitCard;
import com.am.MyBankPlus.model.BankCard;
import com.am.MyBankPlus.model.Card;

public class Main {
    public static void main(String[] args) {
        BankCard debitGold = new DebitCard(new Card());
        BankCard creditAccumulation = new CreditCard(new Card());

        System.out.println("---CREDIT-DEBIT---");
        System.out.println(creditAccumulation.checkAllBalance());
        creditAccumulation.addBalance(5000);
        System.out.println(creditAccumulation.checkAllBalance());
        creditAccumulation.pay(5000);
        System.out.println(creditAccumulation.checkAllBalance());
        creditAccumulation.pay(3000);
        System.out.println(creditAccumulation.checkAllBalance());
        creditAccumulation.addBalance(2000);
        System.out.println(creditAccumulation.checkAllBalance());
        creditAccumulation.addBalance(2000);
        System.out.println(creditAccumulation.checkAllBalance());


        System.out.println("\n---DEBIT---");

        System.out.println(debitGold.checkAllBalance());
        debitGold.addBalance(15000);
        System.out.println(debitGold.checkAllBalance());
        debitGold.pay(5000);
        System.out.println(debitGold.checkAllBalance());
        debitGold.pay(3000);
        System.out.println(debitGold.checkAllBalance());
        debitGold.addBalance(2000);
        System.out.println(debitGold.checkAllBalance());
        debitGold.addBalance(2000);
        System.out.println(debitGold.checkAllBalance());
        debitGold.pay(10000);
        System.out.println(debitGold.checkAllBalance());
    }
}
