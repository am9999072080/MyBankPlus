package com.am.MyBankPlus.service.impl;

import com.am.MyBankPlus.model.ClientScore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class GiniBankMetrics {
    /**
     * Расчёт индекса Джини для бинарной классификации клиентов банка. * * @param clients Список клиентов с информацией о значении (остатке на счёте/долг) и статусе клиента (хороший/плохой). * @return Коэффициент Джини.
     */

    public double calculateGini(List<ClientScore> clients) {
        Collections.sort(clients, (a, b) -> Double.compare(a.getValue(), b.getValue()));

        long goodClientsCount = clients.stream().filter(ClientScore::isGoodClient).count();
        long badClientsCount = clients.size() - goodClientsCount;
        long totalClients = clients.size();

        if (goodClientsCount == 0 || badClientsCount == 0) {
            return 0.0;
        }

        double probGood = (double) goodClientsCount / totalClients;
        double probBad = (double) badClientsCount / totalClients;

        return 1.0 - (probGood * probGood + probBad * probBad);
    }
}
