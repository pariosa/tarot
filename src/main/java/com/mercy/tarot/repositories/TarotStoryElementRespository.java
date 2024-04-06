package com.mercy.tarot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercy.tarot.models.TarotStoryElements;

@Repository
public interface TarotStoryElementRespository extends JpaRepository<TarotStoryElements, Long> {
    // Repository methods...
}