package com.sungmook.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

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

    public enum Type {
        SIGNUP, FIND_PASSWORD
    }

    private AuthToken(){};

    public static final int EXPIRE_HOUR = 3;        // 토근 유효 시간 : 3시간.

    /**
     * http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
     */
    public AuthToken(User user){
        SecureRandom random = new SecureRandom();
        this.token = new BigInteger(130, random).toString(32);

        this.createdDate = new Date();
        this.user = user;
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String token;

    @CreatedDate
    private Date createdDate;

    private Date confirmDate;

}
