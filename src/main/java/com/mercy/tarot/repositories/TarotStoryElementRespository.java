package com.mercy.tarot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.mercy.tarot.models.TarotStoryElements;

@Repository
public interface TarotStoryElementRespository extends JpaRepository<TarotStoryElements, Long> {

    TarotStoryElements findByTitleIgnoreCase(String title);

    TarotStoryElements findByTitle(String title);

    // TarotStoryElements findByCardName(String title);

    // TarotStoryElements findCardByNameInString(String title);

    // Repository methods...
    @NonNull
    List<TarotStoryElements> findAll();

    // List<TarotStoryElements> findByCardNameInList(List<String> title);

    // TarotStoryElements findCardByNameIn(List<String> title);

}