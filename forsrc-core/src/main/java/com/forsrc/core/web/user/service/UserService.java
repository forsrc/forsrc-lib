package com.forsrc.core.web.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.forsrc.core.base.service.BaseService;
import com.forsrc.pojo.User;
import com.forsrc.pojo.UserPrivacy;

@Transactional
@Service
public interface UserService extends BaseService<User, Long>{

    @Transactional(transactionManager = "transactionManager", value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
    void save(User user, byte[] password);

    @Transactional(transactionManager = "transactionManager", propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    UserPrivacy findByUsername(String username);
}
