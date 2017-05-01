package com.forsrc.core.web.user.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.forsrc.core.base.dao.BaseDao;
import com.forsrc.pojo.Role;

@Repository
@CacheConfig(cacheNames = "ehcache_pojo")
public interface RoleDao extends BaseDao<Role, Long> {

    @Cacheable
    List<Role> getRoles();

    List<Role> findRoleNamesByUserId(Long userId);
}
