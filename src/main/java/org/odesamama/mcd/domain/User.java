package org.odesamama.mcd.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name = "user_id")
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "user_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "users_seq_gen")
    private Long userId;

    @Column(name = "user_uid")
    private String userUid;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<File> fileList;


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

    @Transient
    public boolean isNew() {
        return userId == null;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
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
