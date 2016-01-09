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
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Created by starnakin on 25.09.2015.
 */

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @Column(name = "group_id")
    @SequenceGenerator(name = "groups_seq_gen", sequenceName = "group_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "groups_seq_gen")
    private Long groupId;

    @Column(name = "group_name")
    @Size(max = 254)
    private String groupName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Group group = (Group) o;

        return new EqualsBuilder().append(groupId, group.groupId).append(groupName, group.groupName)
                .append(user, group.user).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(groupId).append(groupName).append(user).toHashCode();
    }

}
