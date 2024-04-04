package com.matager.app.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceCacheImpl {

    private final UserRepository userRepository;

    @Cacheable(value = "users", key = "#uuid")
    public User getUserByUuid(String uuid) {
        return userRepository.findByUuid(uuid).orElseThrow(() -> new IllegalArgumentException("User does not exist."));
    }
}
