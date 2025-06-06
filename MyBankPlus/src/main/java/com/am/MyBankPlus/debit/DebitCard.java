package com.am.MyBankPlus.debit;

import com.am.MyBankPlus.model.BankCard;
import com.am.MyBankPlus.model.Card;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DebitCard extends BankCard {
    public DebitCard(Card card) {
        super(card);
    }


    @Override
    public void addBalance(double amount) throws RuntimeException {
        if (amount <= 0) {
            throw new RuntimeException("Введенное число должен быть > 0");
        } else {
            getCard().setBalance(getCard().getBalance() + amount);
            System.out.print("ПОПОЛНЕНИЕ: " + amount);
            double d = amount * getPercentAccumulation();
            getCard().setBonus(getCard().getBonus() + d);
            System.out.print(", Накопление от пополнения: " + d);
            System.out.print(", Баланс накопления: " + getCard().getBonus());
        }
    }

    @Override
    public double checkBalance() {
        return getCard().getBalance();
    }

    @Override
    public String checkAllBalance() {
        return "ИНФОРМАЦИЯ О ПОСЛЕДНОЙ УСПЕШНОЙ АВТОРИЗАЦИИ В MyBank:" + getCard().getDate() + ":  " + super.checkAllBalance() + " BONUS: " + getCard().getBonus() + "\n";
    }

    @Override
    public boolean pay(double amount) throws NullPointerException {
        double d;
        if (getCard().getBalance() >= amount) {
            d = amount * getCashBackForBuy();
            getCard().setCashBack(getCard().getCashBack() + d);
            getCard().setBalance(getCard().getBalance() - amount);
            System.out.print("ПОКУПКА: " + -amount);
            System.out.print(", Базовый кешбэк: " + d);
        } else if (amount < 5000) {
            d = amount * getPotentialCash();
            System.out.print(", Потенциал кешбэк: " + d);
            getCard().setCashBack(getCard().getCashBack() + d);

            System.out.print(", Кешбэк всего: " + getCard().getCashBack());
        } else {
            throw new NullPointerException("Недостаточно средств!");
        }
        return true;
    }
}
