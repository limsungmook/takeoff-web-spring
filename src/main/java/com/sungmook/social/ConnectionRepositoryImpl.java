package com.sungmook.social;

import com.sungmook.domain.User;
import com.sungmook.repository.SocialUserConnectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public class ConnectionRepositoryImpl implements ConnectionRepository{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Long memberId;

    private ConnectionFactoryLocator connectionFactoryLocator;

    private SocialUserConnectionRepository socialUserConnectionRepository;

    private TextEncryptor textEncryptor;

    public ConnectionRepositoryImpl(){
        logger.debug("커넥트레포지토리 기본 생성자");
    }

    public ConnectionRepositoryImpl(Long memberId, ConnectionFactoryLocator connectionFactoryLocator, SocialUserConnectionRepository socialUserConnectionRepository, TextEncryptor textEncryptor) {
        logger.debug("커넥트레포지토리 멤버 생성자");
        this.memberId = memberId;
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.socialUserConnectionRepository = socialUserConnectionRepository;
        this.textEncryptor = textEncryptor;
    }

    @Override
    public MultiValueMap<String, Connection<?>> findAllConnections() {

        List<SocialUserConnection> socialUserConnections = socialUserConnectionRepository.findByUserIdOrderByProviderIdAscRankAsc(memberId);

        List<Connection<?>> resultList = connectionMapper.mapEntities( socialUserConnections );
        MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();
        for (Connection<?> connection : resultList) {
            String providerId = connection.getKey().getProviderId();
            connections.add(providerId, connection);
        }
        return connections;
    }

    @Override
    public List<Connection<?>> findConnections(String providerId) {
        return connectionMapper.mapEntities(socialUserConnectionRepository.findByUserIdAndProviderIdOrderByRank(memberId, providerId));
    }

    @Override
    public <A> List<Connection<A>> findConnections(Class<A> apiType) {
        List<?> connections = findConnections(getProviderId(apiType));
        return (List<Connection<A>>) connections;
    }

    @Override
    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUserIds) {

        if (providerUserIds.isEmpty()) {
            throw new IllegalArgumentException("Unable to execute find: no providerUsers provided");
        }

        throw new RuntimeException("사용 안됨") ;
    }

    @Override
    public Connection<?> getConnection(ConnectionKey connectionKey) {
        SocialUserConnection socialUserConnection = socialUserConnectionRepository.findByUserIdAndProviderIdAndProviderUserId(memberId, connectionKey.getProviderId(), connectionKey.getProviderUserId());
        if(socialUserConnection == null){
            throw new NoSuchConnectionException(connectionKey);
        }
        return connectionMapper.mapEntity(socialUserConnection);
    }

    @Override
    public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
        String providerId = getProviderId(apiType);
        return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
    }

    @Override
    public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {

        String providerId = getProviderId(apiType);
        Connection<A> connection = (Connection<A>) findPrimaryConnection(providerId);
        if (connection == null) {
            throw new NotConnectedException(providerId);
        }
        return connection;
    }

    @Override
    public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
        String providerId = getProviderId(apiType);
        return (Connection<A>) findPrimaryConnection(providerId);
    }

    @Transactional(readOnly = true )
    private Connection<?> findPrimaryConnection(String providerId) {
        List<Connection<?>> connections = connectionMapper.mapEntities(socialUserConnectionRepository.findByUserIdAndProviderIdAndRank(memberId, providerId, 1));
        if (connections.size() > 0) {
            return connections.get(0);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void addConnection(Connection<?> connection) {
        ConnectionData data = connection.createData();
        List<Long> connIds = socialUserConnectionRepository.findIdByProviderIdAndProviderUserId(data.getProviderId(), data.getProviderUserId());

        if( connIds != null && connIds.size() > 0 ){
            removeConnections(data.getProviderId());
            //throw new RuntimeException("이미 커넥션이 존재합니다");
        }
        try {
            System.out.println("Data 가져오기");


            int rank = getRank(data.getProviderId()) ;
            SocialUserConnection socialUserConnection = new SocialUserConnection();
            socialUserConnection.setUser(new User(memberId));
            socialUserConnection.setProviderId(data.getProviderId());
            socialUserConnection.setProviderUserId(data.getProviderUserId());
            socialUserConnection.setRank(rank);
            socialUserConnection.setDisplayName(data.getDisplayName());
            socialUserConnection.setProfileUrl(data.getProfileUrl());
            socialUserConnection.setImageUrl(data.getImageUrl());
            socialUserConnection.setAccessToken(encrypt(data.getAccessToken()));
            socialUserConnection.setSecret(encrypt(data.getSecret()));
            socialUserConnection.setRefreshToken(encrypt(data.getRefreshToken()));
            socialUserConnection.setExpireTime(data.getExpireTime());
            socialUserConnectionRepository.save(socialUserConnection);

        } catch (DuplicateKeyException e) {
            throw new DuplicateConnectionException(connection.getKey());
        } catch ( Exception ex){
            ex.printStackTrace();
        }
    }

    @Transactional( readOnly = true )
    private int getRank(String providerId) {
        Integer rank = socialUserConnectionRepository.findTopRankByUserIdAndProviderId(memberId, providerId);
        if( rank == null ){
            rank = 1;
        }
        return rank + 1;
    }


    @Override
    @Transactional
    public void updateConnection(Connection<?> connection) {

        ConnectionData data = connection.createData();

        SocialUserConnection socialUserConnection = socialUserConnectionRepository.findByUserIdAndProviderIdAndProviderUserId(memberId, data.getProviderId(), data.getProviderUserId());
        if( socialUserConnection == null ){
            return;
        }
        socialUserConnection.setDisplayName(data.getDisplayName());
        socialUserConnection.setProfileUrl(data.getProfileUrl());
        socialUserConnection.setImageUrl(data.getImageUrl());
        socialUserConnection.setAccessToken(encrypt(data.getAccessToken()));
        socialUserConnection.setSecret(encrypt(data.getSecret()));
        socialUserConnection.setRefreshToken(encrypt(data.getRefreshToken()));
        socialUserConnection.setExpireTime(data.getExpireTime());
    }

    @Override
    public void removeConnections(String providerId) {
        List<SocialUserConnection> socialUserConnections = socialUserConnectionRepository.findByUserIdAndProviderId(memberId, providerId);
        socialUserConnectionRepository.delete(socialUserConnections);
    }

    @Override
    public void removeConnection(ConnectionKey connectionKey) {
        SocialUserConnection socialUserConnection = socialUserConnectionRepository.findByUserIdAndProviderIdAndProviderUserId(memberId, connectionKey.getProviderId(), connectionKey.getProviderUserId());
        socialUserConnectionRepository.delete(socialUserConnection);
    }


    private <A> String getProviderId(Class<A> apiType) {
        return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
    }

    private String encrypt(String text) {
        return text != null ? textEncryptor.encrypt(text) : text;
    }

    private final ServiceProviderConnectionMapper connectionMapper = new ServiceProviderConnectionMapper();

    @Transactional
    private final class ServiceProviderConnectionMapper {
        public List<Connection<?>> mapEntities(List<SocialUserConnection> socialUserConnections) {
            List<Connection<?>> result = new ArrayList<Connection<?>>();
            for (SocialUserConnection socialUserConnection : socialUserConnections) {
                result.add(mapEntity(socialUserConnection));
            }
            return result;
        }

        public Connection<?> mapEntity(SocialUserConnection socialUserConnection) {
            ConnectionData connectionData = mapConnectionData(socialUserConnection);
            ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(connectionData
                    .getProviderId());
            return connectionFactory.createConnection(connectionData);
        }

        private ConnectionData mapConnectionData(SocialUserConnection socialUserConnection) {
            return new ConnectionData(socialUserConnection.getProviderId(), socialUserConnection.getProviderUserId(),
                    socialUserConnection.getDisplayName(), socialUserConnection.getProfileUrl(), socialUserConnection.getImageUrl(),
                    decrypt(socialUserConnection.getAccessToken()), decrypt(socialUserConnection.getSecret()),
                    decrypt(socialUserConnection.getRefreshToken()), expireTime(socialUserConnection.getExpireTime()));
        }

        private String decrypt(String encryptedText) {
            return encryptedText != null ? textEncryptor.decrypt(encryptedText) : encryptedText;
        }

        private Long expireTime(Long expireTime) {
            return expireTime == null || expireTime == 0 ? null : expireTime;
        }
    }
}
