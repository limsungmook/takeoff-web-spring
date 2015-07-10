package com.sungmook.repository;

import com.sungmook.social.SocialUserConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public interface SocialUserConnectionRepository extends JpaRepository<SocialUserConnection, Long>{

    List<Long> findMemberIdByProviderIdAndProviderUserId(String providerId, String providerUserId);

    List<Long> findMemberIdByProviderIdAndProviderUserIdIn(String providerId, Set<String> providerUserIds);

    List<SocialUserConnection> findByMemberIdOrderByProviderIdAndRank(Long memberId);

    List<SocialUserConnection> findByMemberIdAndProviderIdOrderByRank(Long memberId, String providerId);

    SocialUserConnection findByMemberIdAndProviderIdAndProviderUserId(Long memberId, String providerId, String providerUserId);

    List<SocialUserConnection> findByUserIdAndProviderIdAndRank(Long memberId, String providerId, int rank);

    List<Long> findIdByProviderIdNProviderUserId(String providerId, String providerUserId);

    Integer findTopRankByMemberIdAndProviderId(Long memberId, String providerId);

    List<SocialUserConnection> findByMemberIdAndProviderId(Long memberId, String providerId);
}
