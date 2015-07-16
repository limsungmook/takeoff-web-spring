package com.sungmook.social;

import com.sungmook.repository.SocialUserConnectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.*;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 *
 * spring-social 에서는 기본적으로 JdbcUsersConnectionRepository 와 InMemory ... 만 제공해준다.
 * DB 에 상관없이 독립적인 애플리케이션을 만들고 싶다면 jpa 를 사용해 구현해야 하기 때문에 커스텀하게 만들었다.
 * @see JdbcUsersConnectionRepository
 *
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */

@Primary
@Repository("UsersConnectionRepository")
public class UsersConnectionRepositoryImpl implements UsersConnectionRepository {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public UsersConnectionRepositoryImpl(){
        logger.debug("유저스커넥션레포지토리 기본 생성자");
    }


    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    private TextEncryptor textEncryptor;

    @Autowired
    private SocialUserConnectionRepository socialUserConnectionRepository;

    @Autowired
    private ConnectionSignUp connectionSignUp;


    @Override
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        ConnectionKey key = connection.getKey();
        List<Long> localUserIds = socialUserConnectionRepository.getUserIdByProviderIdAndProviderUserId(key.getProviderId(), key.getProviderUserId());
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

        final List<Long> localUserIds = socialUserConnectionRepository.findUserIdByProviderIdAndProviderUserIdIn(providerId, providerUserIds);

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
        return new ConnectionRepositoryImpl(Long.valueOf(userId), connectionFactoryLocator, socialUserConnectionRepository, textEncryptor);
    }
}
