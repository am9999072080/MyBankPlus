package com.am.MyBankPlus.model;

import lombok.Data;


@Data
public class ClientScore {
    private final double value;
    private final boolean isGoodClient;

    public ClientScore(double value, boolean isGoodClient) {
        this.value = value;
        this.isGoodClient = isGoodClient;
    }

    public boolean isGoodClient() {
        return isGoodClient;
    }
}
