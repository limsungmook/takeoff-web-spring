package com.sungmook.social;

import com.sungmook.repository.SocialUserConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.*;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */

@Component
public class UsersConnectionRepositoryImpl implements UsersConnectionRepository {

    @Autowired
    private SocialUserConnectionRepository socialUserConnectionRepository;

    @Autowired
    private ConnectionSignUp connectionSignUp;


    @Override
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        ConnectionKey key = connection.getKey();
        List<Long> localUserIds = socialUserConnectionRepository.findMemberIdByProviderIdAndProviderUserId(key.getProviderId(), key.getProviderUserId());
        if (localUserIds.size() == 0 && connectionSignUp != null) {
            String newUserId = connectionSignUp.execute(connection);
            if (newUserId != null)
            {
                createConnectionRepository(newUserId).addConnection(connection);
                return Arrays.asList(newUserId);
            }
        }

        List<String> newList = new ArrayList<String>(localUserIds.size());
        for(Long userId : localUserIds ){
            newList.add(String.valueOf(userId));
        }

        return newList;
    }

    @Override
    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {

        final List<Long> localUserIds = socialUserConnectionRepository.findMemberIdByProviderIdAndProviderUserIdIn(providerId, providerUserIds);

        Set<String> newSets = new HashSet<String>();
        for(Long userId : localUserIds ){
            newSets.add(String.valueOf(userId));
        }

        return newSets;
    }

    @Override
    public ConnectionRepository createConnectionRepository(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return new ConnectionRepositoryImpl(Long.valueOf(userId));
    }
}
