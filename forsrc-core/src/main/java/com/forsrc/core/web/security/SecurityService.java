package com.forsrc.core.web.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forsrc.core.web.user.service.RoleService;
import com.forsrc.core.web.user.service.UserRoleService;
import com.forsrc.core.web.user.service.UserService;
import com.forsrc.pojo.Role;
import com.forsrc.pojo.UserPrivacy;
import com.forsrc.pojo.UserRole;

@Service
public class SecurityService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;

    public UserPrivacy findByUsername(String username) {
        return userService.findByUsername(username);
    }

    public List<UserRole> findByUserId(Long userId) {
        return userRoleService.findByUserId(userId);
    }

    public List<Role> getRoles() {
        return roleService.getRoles();
    }

    public List<Role> findRoleNamesByUserId(Long userId) {
        return roleService.findRoleNamesByUserId(userId);
    }
}
