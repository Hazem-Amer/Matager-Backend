package com.matager.app.Item.products.userclicks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProductClicksRepository extends JpaRepository<UserProductClicks, Long> {
    Optional<UserProductClicks> findByUserIdAndItemId(Long userId, Long itemId);
}
