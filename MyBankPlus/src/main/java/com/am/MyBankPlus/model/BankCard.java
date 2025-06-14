package com.am.MyBankPlus.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public abstract class BankCard {
    Card card;
    private final double bigAmount = 5000;
    private final double cashBackForBuy = 0.001;
    private final double potentialCash = 0.003;
    private final double percentAccumulation = 0.001;
    private double accumulation;

    public BankCard(Card card) {
        this.card = card;
    }

    /**
     * Adding bank account
     */
    public abstract void addBalance(double amount);

    /**
     * Amount on the account
     *
     * @return balance
     */
    public abstract double checkBalance();

    /**
     * @return true / false
     */
    public boolean pay(double amount) throws RuntimeException {
        if (amount <= 0 || !Double.isFinite(amount)) {
            throw new NumberFormatException("Введенное число должен быть > 0");
        }
        return true;
    }

    /**
     * All Balance
     *
     * @return balance debit, balance credit
     */
    public String checkAllBalance() {
        return checkBalance() + "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankCard bankCard = (BankCard) o;
        return Objects.equals(card, bankCard.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(card);
    }
}
