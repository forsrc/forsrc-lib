package com.forsrc.core.web.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.forsrc.core.base.dao.BaseDao;
import com.forsrc.pojo.Role;

@Repository
public interface RoleDao extends BaseDao<Role, Long> {

    List<Role> getRoles();

    List<Role> findByUserId(Long userId);
}
