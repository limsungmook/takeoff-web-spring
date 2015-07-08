package com.sungmook.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Entity
@Data
public class AuthToken {


    private AuthToken(){};

    public static final int EXPIRE_HOUR = 3;        // 토근 유효 시간 : 3시간.

    /**
     * http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
     */
    public AuthToken(Member member){
        SecureRandom random = new SecureRandom();
        this.token = new BigInteger(130, random).toString(32);

        this.createdDate = new Date();
        this.member = member;
    }

    public enum Type {
        SIGNUP, FIND_PASSWORD
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Member member;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String token;

    private Date createdDate;

    private Date confirmDate;

}
