package com.forsrc.core.web.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.forsrc.core.base.service.BaseService;
import com.forsrc.pojo.Role;

@Transactional
@Service
public interface RoleService extends BaseService<Role, Long>{

    @Transactional(transactionManager = "transactionManager", propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    List<Role> getRoles();

    @Transactional(transactionManager = "transactionManager", propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    List<Role> findByUserId(Long userId);
}
