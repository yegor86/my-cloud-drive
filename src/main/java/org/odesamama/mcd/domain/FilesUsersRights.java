package org.odesamama.mcd.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.odesamama.mcd.domain.enums.Permissions;

import javax.persistence.*;

/**
 * Created by starnakin on 25.09.2015.
 */

@Entity
@Table(name ="files_user_rights")
public class FilesUsersRights {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "file_ur_seq_gen", sequenceName = "file_ur_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "file_ur_seq_gen")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File fileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User ownerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group groupId;

    @Column(name = "permission")
    private Permissions permission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(User ownerId) {
        this.ownerId = ownerId;
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

    public File getFileId() {
        return fileId;
    }

    public void setFileId(File fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FilesUsersRights that = (FilesUsersRights) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(fileId, that.fileId)
                .append(ownerId, that.ownerId)
                .append(groupId, that.groupId)
                .append(permission, that.permission)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(fileId)
                .append(ownerId)
                .append(groupId)
                .append(permission)
                .toHashCode();
    }
}
