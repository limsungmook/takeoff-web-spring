package com.sungmook.repository;

import com.sungmook.domain.ContentReadUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public interface ContentReadUserRepository extends JpaRepository<ContentReadUser, Long> {
    ContentReadUser findOneByContentIdAndUserId(Long id, Long memberId);
}
