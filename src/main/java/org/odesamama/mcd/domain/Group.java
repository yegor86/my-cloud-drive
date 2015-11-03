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

    @Column(name = "description")
    @Size(max = 254)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Group group = (Group) o;

        return new EqualsBuilder().append(groupId, group.groupId).append(description, group.description)
                .append(owner, group.owner).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(groupId).append(description).append(owner).toHashCode();
    }

}
