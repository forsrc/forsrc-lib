package com.forsrc.pojo;

import java.text.MessageFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

/**
 * The type UserPrivacy.
 */

@Entity
@Table(name = "t_user_privacy", indexes = {
        @Index(name = "index_user_privacy_username", columnList = "username") }, uniqueConstraints = {
                @UniqueConstraint(columnNames = { "username" }) })
public class UserPrivacy implements java.io.Serializable {

    private static final long serialVersionUID = 2623443497934460034L;
    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", unique = true, nullable = true, insertable = false, updatable = false)
    private Long userId;

    @Column(name = "username", unique = true, length = 200, nullable = false)
    private String username;

    @Column(name = "password", length = 200, insertable = true, updatable = false, nullable = false)
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_on", insertable = true, updatable = false, nullable = false, columnDefinition = "DATE DEFAULT CURRENT_TIMESTAMP")
    private Date createOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_on", insertable = false, updatable = true, nullable = false, columnDefinition = "DATE DEFAULT CURRENT_TIMESTAMP")
    private Date updateOn;

    @Column(name = "version")
    @Version
    private int version;

    @Column(name = "status", length = 1, nullable = false, columnDefinition = "INT DEFAULT 1")
    private int status; // 0: delete; 1: OK; 2: NG

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    // Constructors

    /**
     * default constructor
     */
    public UserPrivacy() {
    }

    public UserPrivacy(Long userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    /**
     * Instantiates a new User.
     *
     * @param id
     *            the id
     */
    public UserPrivacy(Long id) {
        this.id = id;
    }

    // Property accessors

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets id.
     *
     * @param id
     *            the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username
     *            the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password
     *            the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets update on.
     *
     * @return the update on
     */
    public Date getUpdateOn() {
        return updateOn;
    }

    /**
     * Sets update on.
     *
     * @param updateOn
     *            the update on
     */
    public void setUpdateOn(Date updateOn) {
        this.updateOn = updateOn;
    }

    /**
     * Gets create on.
     *
     * @return the create on
     */
    public Date getCreateOn() {
        return createOn;
    }

    /**
     * Sets create on.
     *
     * @param createOn
     *            the create on
     */
    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * Sets version.
     *
     * @param version
     *            the version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return MessageFormat.format("'{'\"id\" : \"{0}\", '\"userId\" : \"{1}\", \"password\" : \"{2}\"'}'", id, userId, password);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status
     *            the status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}