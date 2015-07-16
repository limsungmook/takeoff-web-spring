package com.sungmook.repository;

import com.sungmook.social.SocialUserConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public interface SocialUserConnectionRepository extends JpaRepository<SocialUserConnection, Long>{

    List<Long> getUserIdByProviderIdAndProviderUserId(String providerId, String providerUserId);

    List<Long> findUserIdByProviderIdAndProviderUserIdIn(String providerId, Set<String> providerUserIds);

//    List<SocialUserConnection> findByUserIdOrderByProviderIdAscAndRankAsc(Long userId);

    List<SocialUserConnection> findByUserIdAndProviderIdOrderByRank(Long UserId, String providerId);

    SocialUserConnection findByUserIdAndProviderIdAndProviderUserId(Long UserId, String providerId, String providerUserId);

    List<SocialUserConnection> findByUserIdAndProviderIdAndRank(Long UserId, String providerId, Integer rank);

    List<Long> findIdByProviderIdAndProviderUserId(String providerId, String providerUserId);

    Integer findTopRankByUserIdAndProviderId(Long UserId, String providerId);

    List<SocialUserConnection> findByUserIdAndProviderId(Long UserId, String providerId);

    List<SocialUserConnection> findByUserIdOrderByProviderIdAscRankAsc(Long UserId);
}
