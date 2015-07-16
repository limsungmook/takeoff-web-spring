package com.sungmook.domain;

import com.sungmook.domain.validation.constrant.NoDuplicatedUsername;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 회원가입 처리 클래스
 * Persistent 상태가 되지 않고 입력된 값을 검증하는 용도로 쓰임.
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Data
@ToString
public class SignupUser extends User {

    public SignupUser() {
        this.addRole(Role.buildFromValue(Role.Value.INACTIVE_USER));
    }

    /**
     * Form에 입력된 값을 기반으로 Member 를 만들어 리턴한다.
     *
     * Hibernate 에서는 Persistent 화 할 때 인스턴스의 어노테이션만을 보는 것 같다.
     * memberRepository.save(signupMember) 를 했을 때 다운캐스팅이 일어나며 Member 도메인만 저장되는 것을 원했는데
     * 실제 인스턴스 클래스인 SignupMember 가 @Entity 인지 여부를 보는 바람에 불가능하다.
     * Mybatis 로 하면 문제 없을텐데 하이버네이트가 너무 ORM 을 잘 지원하는듯..
     *
     * @return
     * 새롭게 생성된 Member 인스턴스
     */
    public User buildUser(){
        User user = new User();
        user.setUsername(super.getUsername());
        user.setName(super.getName());
        user.setEncryptedPassword(super.getEncryptedPassword());
        user.setRoles(super.getRoles());
        return user;
    }

    @NoDuplicatedUsername
    @Override
    public String getUsername(){
        return super.getUsername();
    }

    @NotEmpty(message = "{com.sungmook.domain.SignupMember.password.NotEmpty}")
    private String password;

    @NotEmpty(message = "{com.sungmook.domain.SignupMember.reTypePassword.NotEmpty}")
    private String reTypePassword;


    public void setPassword(String password){
        this.password = password;
        this.setEncryptedPasswordFromPassword(password);
    }

}
