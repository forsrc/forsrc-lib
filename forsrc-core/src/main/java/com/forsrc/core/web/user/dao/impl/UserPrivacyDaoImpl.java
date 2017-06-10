package com.forsrc.core.web.user.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.forsrc.core.base.dao.impl.BaseDaoImpl;
import com.forsrc.core.web.user.dao.UserPrivacyDao;
import com.forsrc.core.web.user.dao.mapper.UserPrivacyMapper;
import com.forsrc.pojo.UserPrivacy;

@Repository
public class UserPrivacyDaoImpl extends BaseDaoImpl<UserPrivacy, Long> implements UserPrivacyDao {

    @Autowired
    private UserPrivacyMapper userPrivacyMapper;

    @Override
    public Class<UserPrivacy> getEntityClass() {
        return UserPrivacy.class;
    }

    @Override
    public UserPrivacy findByUsername(String username) {
        //Query query = entityManager.createNamedQuery("sql_user_privacy_findByUsername", UserPrivacy.class);
        //query.setParameter("username", username);
        //query.setFirstResult(0);
        //query.setMaxResults(1);
        //return (UserPrivacy) query.getSingleResult();
        return this.userPrivacyMapper.findByUsername(username);
    }
}
