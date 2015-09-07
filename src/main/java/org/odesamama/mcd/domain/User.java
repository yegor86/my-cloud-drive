package org.odesamama.mcd.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="users")
public class User {
    private Long userId;

    private String userUid;

    private String userName;

    private String userEmail;

    @Id
    @Column(name = "user_id")
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "user_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "users_seq_gen")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "user_uid")
    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "user_email")
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Transient
    public boolean isNew() {
        return userId == null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        return new EqualsBuilder().append(userUid, ((User) obj).userUid).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(userUid).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(userId)
                .append(userUid)
                .append(userName)
                .append(userEmail)
                .toString();
    }
}
