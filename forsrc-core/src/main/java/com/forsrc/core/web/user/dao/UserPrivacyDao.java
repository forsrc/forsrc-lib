package com.forsrc.core.web.user.dao;

import org.springframework.stereotype.Repository;

import com.forsrc.core.base.dao.BaseDao;
import com.forsrc.pojo.UserPrivacy;

@Repository
public interface UserPrivacyDao extends BaseDao<UserPrivacy, Long> {
    UserPrivacy findByUsername(String username);
}
