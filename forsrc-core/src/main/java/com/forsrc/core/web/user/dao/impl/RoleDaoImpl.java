package com.forsrc.core.web.user.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.forsrc.core.base.dao.impl.BaseDaoImpl;
import com.forsrc.core.web.user.dao.RoleDao;
import com.forsrc.core.web.user.dao.mapper.RoleMapper;
import com.forsrc.pojo.Role;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role, Long> implements RoleDao {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Class<Role> getEntityClass() {
        return Role.class;
    }

    @Override
    public List<Role> getRoles() {
        return findAll();
    }

    @Override
    public List<Role> findByUserId(Long userId) {
        //Query query = entityManager.createNamedQuery("sql_role_findRoleNamesByUserId", getEntityClass());
        //query.setParameter("userId", userId);
        //return query.getResultList();
        return this.roleMapper.findByUserId(userId);
    }
}
