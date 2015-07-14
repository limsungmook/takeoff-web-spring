package com.sungmook.service;

import com.sungmook.domain.Story;
import com.sungmook.repository.MemberRepository;
import com.sungmook.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public void save(Story story){
        storyRepository.save(story);
    }
}
