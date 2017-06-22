package com.forsrc.pojo;

import java.text.MessageFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(value = { "user" })
@Entity
//@fome":"admin","email":"admin@forsrc.com","createOn":1497508846046,"updateOn":1497508846046,"status":1,"isAdmin":true,"image":"","version":0,"userRoles":[{"id":1,"userId":1,"roleId":1,"createOn":1497508846230,"updateOn":1497508846230,"version":0,"status":1,"role":{"id":1,"name":"ROLE_ADMIN","parentId":null,"createOn":1497508846215,"updateOn":1497508846215,"status":1,"version":0}}],"admin":true}rmatter:off
@Table(
        name = "t_user_role",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "user_id", "role_id" })
        }
)
//@formatter:on
public class UserRole implements java.io.Serializable {

    private static final long serialVersionUID = 3841772774323550118L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_on", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_on", insertable = false, updatable = false, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date updateOn;

    @Column(name = "version")
    @Version
    private int version;

    @Column(name = "status", length = 1, nullable = false, columnDefinition = "INT DEFAULT 1")
    private int status; // 0: delete; 1: OK; 2: NG

    //@formatter:off
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id",
             unique = true, insertable = false, updatable = false,
             foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)
    )
    //@formatter:on
    private User user;

    //@formatter:off
    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "role_id", referencedColumnName = "id", 
            unique = true, insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "none")
    )
    //@formatter:on
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
        return MessageFormat.format("'{'\"id\" : \"{0}\", \"userId\" : \"{1}\", \"roleId\" : \"{2}\"'}'", id, userId,
                roleId);
    }

}