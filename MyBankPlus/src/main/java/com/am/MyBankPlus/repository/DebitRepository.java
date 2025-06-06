package com.am.MyBankPlus.repository;

import com.am.MyBankPlus.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebitRepository extends JpaRepository<Card, Long> {
}
