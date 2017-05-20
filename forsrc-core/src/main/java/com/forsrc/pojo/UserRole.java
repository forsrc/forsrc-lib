package com.forsrc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.MessageFormat;
import java.util.Date;

@JsonIgnoreProperties(value = {"user", "role"})
public class UserRole implements java.io.Serializable {

    private Long id;
    private Long userId;
    private Long roleId;
    private Date updateOn;
    private Date createOn;
    private int status; // 0: delete; 1: OK; 2: NG
    private int version;

    private User user;
    private Role role;

    public UserRole() {
    }

    public UserRole(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Date getUpdateOn() {
        return updateOn;
    }

    public void setUpdateOn(Date updateOn) {
        this.updateOn = updateOn;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return MessageFormat.format("'{'\"id\" : \"{0}\", \"userId\" : \"{1}\",\"roleId\" : \"{2}\"'}'", id, userId,
                roleId);
    }

}