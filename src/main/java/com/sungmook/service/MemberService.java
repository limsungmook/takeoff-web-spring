package com.sungmook.service;

import com.sungmook.domain.AuthToken;
import com.sungmook.domain.Member;
import com.sungmook.domain.Role;
import com.sungmook.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Service
public class MemberService {

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public void signup(Member member){
        memberRepository.save(member);

        authTokenService.generateAndSendMail(AuthToken.Type.SIGNUP, member);
    }

    @Transactional
    public void confirmSignup(Long memberId) {
        Member member = memberRepository.findOne(memberId);
        Set<Role> roles = member.getRoles();
        roles.clear();
        roles.add(Role.buildFromValue(Role.Value.USER));
    }
}
