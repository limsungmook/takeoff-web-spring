package com.sungmook.config;

import com.sungmook.domain.User;
import com.sungmook.security.SessionUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
    @Bean
    public AuditorAware<User> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return null;
            }

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal.getClass() == String.class) {
                return null;
            }
            return new User(((SessionUser) authentication.getPrincipal()).getUserId());
        };
    }

}

