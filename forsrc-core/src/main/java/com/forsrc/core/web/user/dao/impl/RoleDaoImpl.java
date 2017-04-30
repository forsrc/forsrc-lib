package com.forsrc.core.web.user.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.forsrc.core.base.dao.impl.BaseDaoImpl;
import com.forsrc.core.web.user.dao.RoleDao;
import com.forsrc.pojo.Role;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role, Long> implements RoleDao {

    @Override
    public Class<Role> getEntityClass() {
        return Role.class;
    }

    @Override
    public List<Role> getRoles() {
        Query query = entityManager.createNamedQuery("sql_role_getRoles", getEntityClass());
        return query.getResultList();
    }
}
