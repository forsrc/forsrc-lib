package com.forsrc.core.web.user.service;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.forsrc.pojo.User;
import com.forsrc.pojo.UserPrivacy;

@Transactional
@Service
//@Cacheable(key = "userServiceCache", keyGenerator = "keyGenerator")
//@CacheConfig(cacheNames = "ehcache_1h")
public interface UserService {

    @Transactional(transactionManager = "transactionManager", propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    //@Cacheable()
    long count();

    @Transactional(transactionManager = "transactionManager", value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
    //@CachePut()
    void save(User user);

    @Transactional(transactionManager = "transactionManager", value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
    //@CachePut()
    void save(User user, byte[] password);

    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
    //@CacheEvict()
    void delete(User user);

    @Transactional(transactionManager = "transactionManager", propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    //@Cacheable()
    User get(long id);

    @Transactional(transactionManager = "transactionManager", propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    //@Cacheable()
    List<User> get(int start, int size);

    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
    //@CachePut()
    void update(User user);

    @Transactional(transactionManager = "transactionManager", propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    //@Cacheable()
    UserPrivacy findByUsername(String username);
}
