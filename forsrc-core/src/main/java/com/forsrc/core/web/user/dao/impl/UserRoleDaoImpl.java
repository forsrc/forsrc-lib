package com.forsrc.core.web.user.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.forsrc.core.base.dao.impl.BaseDaoImpl;
import com.forsrc.core.web.user.dao.UserRoleDao;
import com.forsrc.pojo.UserRole;

@Repository
public class UserRoleDaoImpl extends BaseDaoImpl<UserRole, Long> implements UserRoleDao {

    @Override
    public Class<UserRole> getEntityClass() {
        return UserRole.class;
    }

    @Override
    public List<UserRole> findByUserId(Long userId) {
        Query query = entityManager.createNamedQuery("sql_user_role_findByUserId", getEntityClass());
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<UserRole> findRoleNamesByUserId(Long userId) {
        Query query = entityManager.createNamedQuery("sql_user_role_findRoleNamesByUserId", getEntityClass());
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
