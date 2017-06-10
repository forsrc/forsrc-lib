package com.forsrc.core.web.user.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Repository;

import com.forsrc.core.base.dao.BaseDao;
import com.forsrc.pojo.UserRole;

@Repository
@CacheConfig(cacheNames = "ehcache_pojo")
public interface UserRoleDao extends BaseDao<UserRole, Long> {

    List<UserRole> findByUserId(Long userId);

}
