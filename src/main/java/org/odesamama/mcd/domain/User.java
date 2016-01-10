package org.odesamama.mcd.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "user_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "users_seq_gen")
    @JsonIgnore
    private Long userId;

    @Column(name = "user_uid")
    @JsonIgnore
    private String userUid;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_email")
    private String userEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @JsonBackReference
    private Group group;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private List<File> fileList;

    @Column(name = "create_date")
    @JsonIgnore
    private Date created;

    @Column(name = "update_date")
    @JsonIgnore
    private Date updated;

    public User() {

    }

    public User(String userUid) {
        this.userUid = userUid;
    }

    @PreUpdate
    public void preUpdate() {
        updated = new Date();
    }

    @PrePersist
    public void preCreate() {
        created = new Date();
        updated = new Date();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Transient
    @JsonIgnore
    public boolean isNew() {
        return userId == null;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        return new EqualsBuilder().append(userId, user.userId).append(userUid, user.userUid)
                .append(userName, user.userName).append(lastName, user.lastName).append(userEmail, user.userEmail)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(userId).append(userUid).append(userName).append(lastName)
                .append(userEmail).toHashCode();
    }
}
