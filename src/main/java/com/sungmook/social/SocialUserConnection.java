package com.sungmook.social;

import com.sungmook.domain.Member;
import lombok.Data;

import javax.persistence.*;

/**
 * Spring Social 자체에서 제공되는 JdbcUsersConnectionRepository.sql 을 포팅한 모델
 *
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Entity
@Data
public class SocialUserConnection {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Member member;

    private String providerId;

    private String providerUserId;

    private Integer rank;

    private String displayName;

    private String profileUrl;

    private String imageUrl;

    private String accessToken;

    private String secret;

    private String refreshToken;

    private Long expireTime;

}
