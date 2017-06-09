package com.forsrc.core.web.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forsrc.core.base.dao.BaseDao;
import com.forsrc.core.base.service.impl.BaseServiceImpl;
import com.forsrc.core.web.user.dao.UserRoleDao;
import com.forsrc.core.web.user.service.UserRoleService;
import com.forsrc.pojo.UserRole;

@Service
@Transactional
@Cacheable(value = "userRoleService")
@CacheConfig(cacheNames = "ehcache_10m")
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole, Long> implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    @Cacheable(value = "list", key= "#root.targetClass + '/' + #userId")
    public List<UserRole> findByUserId(Long userId) {
        return userRoleDao.findByUserId(userId);
    }

    @Override
    public BaseDao<UserRole, Long> getBaseDao() {
        return this.userRoleDao;
    }
}
