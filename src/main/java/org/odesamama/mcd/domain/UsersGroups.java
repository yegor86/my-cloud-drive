package org.odesamama.mcd.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.odesamama.mcd.domain.enums.Permissions;

/**
 * Created by starnakin on 25.09.2015.
 */

@Entity
@Table(name = "files_user_rights")
public class UsersGroups {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "file_ur_seq_gen", sequenceName = "file_ur_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "file_ur_seq_gen")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group groupId;

    @Column(name = "permission")
    private Permissions permission;

    public UsersGroups() {
    }

    public UsersGroups(File file, User user, Permissions permission) {
        this.user = user;
        this.permission = permission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroupId() {
        return groupId;
    }

    public void setGroupId(Group groupId) {
        this.groupId = groupId;
    }

    public Permissions getPermission() {
        return permission;
    }

    public void setPermission(Permissions permission) {
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        UsersGroups that = (UsersGroups) o;

        return new EqualsBuilder().append(id, that.id).append(user, that.user).append(groupId, that.groupId)
                .append(permission, that.permission).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(user).append(groupId).append(permission).toHashCode();
    }
}
