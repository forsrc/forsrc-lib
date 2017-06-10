package com.forsrc.core.web.user.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.forsrc.core.base.dao.impl.BaseDaoImpl;
import com.forsrc.core.web.user.dao.UserDao;
import com.forsrc.core.web.user.dao.mapper.RoleMapper;
import com.forsrc.core.web.user.dao.mapper.UserMapper;
import com.forsrc.pojo.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public List<User> get(int start, int size) {
         return super.get(start, size);
    }

    @Override
    public List<User> findByUsername(String username) {
        // Query query = entityManager.createNamedQuery("sql_user_findByUserId", getEntityClass());
        // query.setParameter("username", username);
        return this.userMapper.findByUsername(username);
    }
}
