package com.sungmook.social;

import com.sungmook.domain.Member;
import com.sungmook.domain.SocialSignupMember;
import com.sungmook.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Component
public class CustomConnectionSignUp implements ConnectionSignUp {

    @Autowired
    private MemberService memberService;
    /**
     * 만약 해당 소셜 연결에서의 사용자가 존재하지 않을 때 이 클래스를 호출한다.
     * takeoff 는 소셜 로그인/가입 버튼을 눌렀을 때 만약 로컬에 계정이 존재하지 않으면
     * 임의의 아이디로 가입한 뒤 서비스를 이용할 수 있게 해준다.
     *
     * @param connection
     * @return
     */
    @Override
    @Transactional
    public String execute(Connection<?> connection) {
        ConnectionData data = connection.createData();
        Member member = new SocialSignupMember().buildMember();
        member.setName(data.getDisplayName());
        member.setProfilePic(data.getProfileUrl());

        memberService.socialInstantSignup(member);

        return String.valueOf(member.getId());
    }
}
