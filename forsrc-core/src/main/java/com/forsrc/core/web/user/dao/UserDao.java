package com.forsrc.core.web.user.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Repository;

import com.forsrc.core.base.dao.BaseDao;
import com.forsrc.pojo.User;

@Repository
@CacheConfig(cacheNames = "ehcache_pojp")
public interface UserDao extends BaseDao<User, Long> {

    List<User> findByUsername(String username);
}
