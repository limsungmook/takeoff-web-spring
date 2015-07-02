package com.sungmook.service;

import com.sungmook.domain.Member;
import com.sungmook.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public void signup(Member member){
        memberRepository.save(member);
    }
}
