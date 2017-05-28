package com.forsrc.core.web.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forsrc.core.web.user.dao.RoleDao;
import com.forsrc.core.web.user.service.RoleService;
import com.forsrc.pojo.Role;

@Service
@Transactional
@Cacheable(key = "roleServiceCache", keyGenerator = "keyGenerator")
@CacheConfig(cacheNames = "ehcache_10m")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    @Cacheable()
    public List<Role> getRoles() {
        return roleDao.getRoles();
    }

    @Override
    @Cacheable()
    public List<Role> findRoleNamesByUserId(Long userId) {
        return roleDao.findRoleNamesByUserId(userId);
    }
}
