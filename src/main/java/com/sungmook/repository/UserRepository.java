package com.sungmook.repository;

import com.sungmook.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(Long id);

    User findByUsername(String email);
}
