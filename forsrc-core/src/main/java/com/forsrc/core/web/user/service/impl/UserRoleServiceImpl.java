package com.forsrc.core.web.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forsrc.core.web.user.dao.UserRoleDao;
import com.forsrc.core.web.user.service.UserRoleService;
import com.forsrc.pojo.UserRole;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public List<UserRole> findByUserId(Long userId) {
        return userRoleDao.findByUserId(userId);
    }
}
