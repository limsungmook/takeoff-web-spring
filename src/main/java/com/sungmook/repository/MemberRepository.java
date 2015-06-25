package com.sungmook.repository;

import com.sungmook.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String username);
}
