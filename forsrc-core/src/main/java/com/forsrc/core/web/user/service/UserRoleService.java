package com.forsrc.core.web.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.forsrc.core.base.service.BaseService;
import com.forsrc.pojo.UserRole;

@Transactional
@Service
public interface UserRoleService extends BaseService<UserRole, Long>{

    @Transactional(transactionManager = "transactionManager", propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    List<UserRole> findByUserId(Long userId);
}
