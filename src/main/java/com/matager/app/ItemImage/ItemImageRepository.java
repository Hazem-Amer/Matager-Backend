package com.matager.app.ItemImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    Optional<List<ItemImage>> findAllByItemId(Long itemId);
}
