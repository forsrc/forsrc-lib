package com.forsrc.core.web.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forsrc.core.base.dao.BaseDao;
import com.forsrc.core.base.service.impl.BaseServiceImpl;
import com.forsrc.core.web.user.dao.RoleDao;
import com.forsrc.core.web.user.service.RoleService;
import com.forsrc.pojo.Role;

@Service
@Transactional
@Cacheable(cacheNames = "ehcache_10m")
@CacheConfig(cacheNames = "ehcache_10m")
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    @Cacheable(value = "list", key = "#root.targetClass + '/' + 'getRoles'")
    public List<Role> getRoles() {
        return roleDao.getRoles();
    }

    @Override
    @Cacheable(value = "list", key = "#root.targetClass + '/' + #userId")
    public List<Role> findByUserId(Long userId) {
        return roleDao.findByUserId(userId);
    }

    @Override
    public BaseDao<Role, Long> getBaseDao() {

        return this.roleDao;
    }
}
