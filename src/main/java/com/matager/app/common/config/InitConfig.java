/*
 * @Abdullah Sallam
 */

package com.matager.app.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InitConfig {

    @Value("${config.default-data-initialization}")
    private boolean initialize;


    // init widgets
    @Bean
    CommandLineRunner initCongregation() {
        return args -> {
            if (initialize) {
            }
        };
    }


}


