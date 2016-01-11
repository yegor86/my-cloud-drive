package org.odesamama.mcd.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.odesamama.mcd.domain.enums.Permissions;

@Entity
@Table(name = "acl")
@NamedQueries({
        @NamedQuery(name = "Acl.findAclByUserAndGroup", query = "from Acl where user = :user and group = :group") })
public class Acl {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "acl_id_seq_gen", sequenceName = "acl_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "acl_id_seq_gen")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "permissions")
    private int permissions;

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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Permissions getPermissions() {
        return Permissions.valueOf(permissions);
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions.getCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Acl that = (Acl) o;

        return new EqualsBuilder().append(id, that.id).append(user, that.user).append(group, that.group)
                .append(permissions, that.permissions).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(user).append(group).append(permissions).toHashCode();
    }
}