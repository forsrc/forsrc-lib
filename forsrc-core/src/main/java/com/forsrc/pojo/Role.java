package com.forsrc.pojo;

import java.text.MessageFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(name = "t_role", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "parent_id" }) })
public class Role implements java.io.Serializable {

    private static final long serialVersionUID = 1243562252764701123L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 200, nullable = false, columnDefinition = "VARCHAR(200) DEFAULT ''")
    private String name;

    @Column(name = "parent_id", nullable = true)
    private Long parentId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_on", insertable = true, updatable = false, nullable = false, columnDefinition = "DATE DEFAULT CURRENT_TIMESTAMP")
    private Date createOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_on", insertable = false, updatable = true, nullable = false, columnDefinition = "DATE DEFAULT CURRENT_TIMESTAMP")
    private Date updateOn;

    @Column(name = "status", length = 1, nullable = false, columnDefinition = "INT DEFAULT 1")
    private int status; // 0: delete; 1: OK; 2: NG

    @Column(name = "version")
    @Version
    private int version;

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return MessageFormat.format("'{'\"id\" : \"{0}\", \"userId\" : \"{1}\"'}'", id, name);
    }
}