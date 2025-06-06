package com.am.MyBankPlus.credit;

import com.am.MyBankPlus.model.BankCard;
import com.am.MyBankPlus.model.Card;

public class CreditCard extends BankCard {
    private double accumulation;

    public CreditCard(Card card) throws RuntimeException {
        super(card);
    }

    @Override
    public void addBalance(double amount) throws RuntimeException {
        if (amount <= 0) {
            throw new RuntimeException("Введенное число должен быть > 0");
        }
        double d;
        if (getCard().getCreditBalance() + amount <= getCard().getCreditLimit()) {
            getCard().setCreditBalance(getCard().getCreditBalance() + amount);
            System.out.print("ПОПОЛНЕНИЕ: " + amount + ", DEB: 0.0, CRED: " + amount);
            d = amount * getPercentAccumulation();
            accumulation = accumulation + d;
            getCard().setBonus(getCard().getBonus() + d);
            System.out.print(", Накопление от пополнения: " + d);
            System.out.print(", Баланс накопления: " + getCard().getBonus());
        } else {
            double sum = getCard().getCreditBalance() + amount - getCard().getCreditLimit();
            getCard().setCreditBalance(getCard().getCreditLimit());
            getCard().setBalance(getCard().getBalance() + sum);
            System.out.print("ПОПОЛНЕНИЕ: " + amount + ", DEB: " + sum + ", CRED: " + (amount - sum));
            System.out.print(", Остаток кредитных средств " + getCard().getCreditLimit());
            d = amount * getPercentAccumulation();
            accumulation = accumulation + d;
            getCard().setBonus(getCard().getBonus() + d);
            System.out.print(", Накопление от пополнения: " + d);
            System.out.print(", Баланс накопления: " + getCard().getBonus());
        }
    }

    @Override
    public boolean pay(double amount) throws NullPointerException {
        double d;
        if (getCard().getBalance() >= amount) {
            getCard().setBalance(getCard().getBalance() - amount);
            System.out.print("ПОКУПКА: " + amount + ", DEB: " + amount + ", CRED: -0.0");
            cash(amount);
        } else if ((getCard().getBalance() + getCard().getCreditBalance() >= amount)) {
            double remaining = amount - getCard().getBalance();
            getCard().setBalance(0);
            getCard().setCreditBalance(getCard().getCreditBalance() - remaining);
            System.out.print("ПОКУПКА: " + amount);
            System.out.print(", DEB: " + getCard().getBalance());
            System.out.print(", CRED: " + remaining);
            cash(amount);
        } else {
            throw new RuntimeException("Недостаточно средств!");
        }
        return super.pay(amount);
    }

    public void cash(double amount) {
        double d;
        if (amount >= 5000) {
            d = amount * getPotentialCash();
            getCard().setCashBack(getCard().getCashBack() + d);
            System.out.print(", Потенциал кешбэк: " + d);
        } else {
            d = amount * getCashBackForBuy();
            getCard().setCashBack(getCard().getCashBack() + d);
            System.out.print(", Базовый кешбэк: " + d);
        }
    }

    @Override
    public double checkBalance() {
        System.out.print(" ");
        return getCard().getBalance() + getCard().getCreditBalance();
    }


    @Override
    public String checkAllBalance() {
        return "СРЕДСТВА НА ВАШИХ СЧЕТАХ В СИСТЕМЕ MyBank+ НА " + getCard().getDate() + ":  " + "\nDEBIT: " + String.format("%.2f", getCard().getBalance()) + " CREDIT: " + String.format("%.2f", getCard().getCreditBalance()) + " CASHBACK " + " BONUS:: " + String.format("%.2f", accumulation) + "\n";
    }
}
