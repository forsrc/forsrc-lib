package com.forsrc.core.web.user.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forsrc.core.base.dao.BaseDao;
import com.forsrc.core.base.service.impl.BaseServiceImpl;
import com.forsrc.core.web.user.dao.UserDao;
import com.forsrc.core.web.user.dao.UserPrivacyDao;
import com.forsrc.core.web.user.service.UserService;
import com.forsrc.pojo.User;
import com.forsrc.pojo.UserPrivacy;

@Transactional
@Service
@Cacheable(cacheNames = "ehcache_10m")
@CacheConfig(cacheNames = "ehcache_10m")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserPrivacyDao userPrivacyDao;

    @Override
    @CachePut()
    @CacheEvict(value = { "list" }, allEntries = true)
    public void save(User user, byte[] password) {
        LOGGER.info("---> has {} users.", userDao.count());
        userDao.save(user);
        UserPrivacy userPrivacy = new UserPrivacy();
        userPrivacy.setUserId(user.getId());
        byte[] username = user.getUsername().getBytes();
        userPrivacy.setPassword(getPassword(username, password));
        userPrivacy.setUsername(user.getUsername());
        userPrivacyDao.save(userPrivacy);
        LOGGER.info("---> has {0} users.", userDao.count());
        // throw new RuntimeException("Test Transactional");
    }

    private String getPassword(byte[] username, byte[] password) {
        byte[] split = "/".getBytes();
        byte[] pwd = new byte[username.length + split.length + password.length + 1];
        System.arraycopy(username, 0, pwd, 0, username.length);
        System.arraycopy(split, 0, pwd, username.length, split.length);
        System.arraycopy(password, 0, pwd, username.length + split.length, password.length);
        String pw = DigestUtils.md5Hex(pwd);
        return pw;
    }

//    @Override
//    @Cacheable(value = { "list" }, key = "#root.targetClass + '/' + #start + '~' + #size", condition = "#result != null")
//    public List<User> get(int start, int size) {
//        // return userDao.get("select '******' as password, user.id,
//        // user.username, user.email from com.forsrc.pojo.User user", null,
//        // start, size);
//        return userDao.get(start, size);
//    }

    @Override
    @Cacheable(key = "#result.getClassName() + '/' + #username", condition = "#result != null")
    public UserPrivacy findByUsername(String username) {
        return userPrivacyDao.findByUsername(username);
    }

    @Override
    public BaseDao<User, Long> getBaseDao() {
        return this.userDao;
    }

    @Override
    public void cacheClear() {
        LOGGER.info("call --> cacheClear()");
    }
}
