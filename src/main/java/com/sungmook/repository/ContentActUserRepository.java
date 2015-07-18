package com.sungmook.repository;

import com.sungmook.domain.ContentActUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public interface ContentActUserRepository extends JpaRepository<ContentActUser, Long> {
    ContentActUser findOneByContentIdAndUserId(Long id, Long userId);
}
