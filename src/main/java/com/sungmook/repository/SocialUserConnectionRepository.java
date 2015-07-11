package com.sungmook.repository;

import com.sungmook.social.SocialUserConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public interface SocialUserConnectionRepository extends JpaRepository<SocialUserConnection, Long>{

    List<Long> getMemberIdByProviderIdAndProviderUserId(String providerId, String providerUserId);

    List<Long> findMemberIdByProviderIdAndProviderUserIdIn(String providerId, Set<String> providerUserIds);

//    List<SocialUserConnection> findByMemberIdOrderByProviderIdAscAndRankAsc(Long memberId);

    List<SocialUserConnection> findByMemberIdAndProviderIdOrderByRank(Long memberId, String providerId);

    SocialUserConnection findByMemberIdAndProviderIdAndProviderUserId(Long memberId, String providerId, String providerUserId);

    List<SocialUserConnection> findByMemberIdAndProviderIdAndRank(Long memberId, String providerId, Integer rank);

    List<Long> findIdByProviderIdAndProviderUserId(String providerId, String providerUserId);

    Integer findTopRankByMemberIdAndProviderId(Long memberId, String providerId);

    List<SocialUserConnection> findByMemberIdAndProviderId(Long memberId, String providerId);

    List<SocialUserConnection> findByMemberIdOrderByProviderIdAscRankAsc(Long memberId);
}
