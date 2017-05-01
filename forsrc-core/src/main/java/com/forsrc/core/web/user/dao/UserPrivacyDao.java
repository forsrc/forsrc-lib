package com.forsrc.core.web.user.dao;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Repository;

import com.forsrc.core.base.dao.BaseDao;
import com.forsrc.pojo.UserPrivacy;

@Repository
@CacheConfig(cacheNames = "ehcache_pojo")
public interface UserPrivacyDao extends BaseDao<UserPrivacy, Long> {
    UserPrivacy findByUsername(String username);
}
