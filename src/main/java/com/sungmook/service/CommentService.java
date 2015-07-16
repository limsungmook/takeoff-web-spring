package com.sungmook.service;

import com.sungmook.domain.Comment;
import com.sungmook.repository.CommentRepository;
import com.sungmook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void save(Comment comment){
        commentRepository.save(comment);
    }

    @Transactional
    public Comment findById(Long id) {
        return commentRepository.findOne(id);
    }
}
