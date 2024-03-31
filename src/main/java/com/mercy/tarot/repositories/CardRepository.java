package com.mercy.tarot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercy.tarot.models.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    // Repository methods...
}