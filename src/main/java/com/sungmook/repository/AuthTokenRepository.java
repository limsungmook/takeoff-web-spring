package com.sungmook.repository;

import com.sungmook.domain.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    AuthToken findByToken(String token);
}
