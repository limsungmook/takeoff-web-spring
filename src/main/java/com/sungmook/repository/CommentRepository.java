package com.sungmook.repository;

import com.sungmook.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
