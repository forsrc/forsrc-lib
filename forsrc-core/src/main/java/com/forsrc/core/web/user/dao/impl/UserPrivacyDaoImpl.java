package com.forsrc.core.web.user.dao.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.forsrc.core.base.dao.impl.BaseDaoImpl;
import com.forsrc.core.web.user.dao.UserPrivacyDao;
import com.forsrc.pojo.UserPrivacy;

@Repository
public class UserPrivacyDaoImpl extends BaseDaoImpl<UserPrivacy, Long> implements UserPrivacyDao {

    @Override
    public Class<UserPrivacy> getEntityClass() {
        return UserPrivacy.class;
    }

    @Override
    public UserPrivacy findByUsername(String username) {
        Query query = entityManager.createNamedQuery("sql_user_privacy_findByUsername", UserPrivacy.class);
        query.setParameter("username", username);
        query.setFirstResult(0);
        query.setMaxResults(1);
        return (UserPrivacy) query.getSingleResult();
    }
}
