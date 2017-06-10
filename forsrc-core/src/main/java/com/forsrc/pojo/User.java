package com.forsrc.pojo;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type User.
 */
// @Entity
// @Table(name = "user")
// @Document(coreName="collection1")
@JsonIgnoreProperties(value = { "userPrivacy" })
@Entity
@Table(name = "t_user", indexes = {
        @Index(name = "index_user_username", columnList = "username") }, uniqueConstraints = {
                @UniqueConstraint(columnNames = { "username" }) })
public class User implements java.io.Serializable {

    private static final long serialVersionUID = 6262590663128115986L;

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    // @Indexed(solrType="text_general")
    @Column(name = "username", unique = true, length = 200, nullable = false)
    private String username;

    @Column(name = "email", unique = true, length = 200, nullable = true)
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_on", insertable = true, updatable = false, nullable = false, columnDefinition = "DATE DEFAULT CURRENT_TIMESTAMP")
    private Date createOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_on", insertable = false, updatable = true, nullable = false, columnDefinition = "DATE DEFAULT CURRENT_TIMESTAMP")
    private Date updateOn;

    @Column(name = "status", length = 1, nullable = false, columnDefinition = "INT DEFAULT 1")
    private int status; // 0: delete; 1: OK; 2: NG

    @Column(name = "is_admin", length = 1, nullable = false, columnDefinition = "INT DEFAULT 0")
    private boolean isAdmin;

    @Column(name = "image", length = 500, nullable = false, columnDefinition = "VARCHAR(500) DEFAULT ''")
    private String image;

    @Column(name = "version")
    @Version
    private int version;

    @OneToMany(targetEntity = UserPrivacy.class)
    @OrderBy("role_id ASC")
    @JoinColumn(name = "id", unique = true)
    private UserPrivacy userPrivacy;

    @OneToMany(targetEntity = UserRole.class)
    @OrderBy("role_id ASC")
    @JoinColumn(name = "id", unique = true)
    private Set<UserRole> userRoles;

    // Constructors

    /**
     * default constructor
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param id
     *            the id
     */
    public User(Long id) {
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

    /**
     * Is admin boolean.
     *
     * @return the boolean
     */
    public boolean isAdmin() {
        return isAdmin;
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
     * Sets admin.
     *
     * @param admin
     *            the admin
     */
    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    /**
     * Gets is admin.
     *
     * @return the is admin
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * Sets is admin.
     *
     * @param admin
     *            the admin
     */
    public void setIsAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email
     *            the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image
     *            the image
     */
    public void setImage(String image) {
        this.image = image;
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

    public UserPrivacy getUserPrivacy() {
        return userPrivacy;
    }

    public void setUserPrivacy(UserPrivacy userPrivacy) {
        this.userPrivacy = userPrivacy;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return MessageFormat.format("'{'\"id\" : \"{0}\", \"email\" : \"{1}\"'}'", id, email);
    }

}