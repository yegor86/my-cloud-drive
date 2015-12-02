package org.odesamama.mcd.domain;

import java.net.URLConnection;
import java.util.Date;

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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Created by starnakin on 25.09.2015.
 */

@Entity
@Table(name = "files")
@NamedQueries({
        @NamedQuery(name = "File.getFileInfoByFilePathAndEmail", query = "from File where owner.userEmail = :email and path = :path"),
        @NamedQuery(name = "File.getFilesListForGivenFolder", query = "from File where parent = :parent") })
public class File {

    @Id
    @Column(name = "file_id")
    @SequenceGenerator(name = "files_seq_gen", sequenceName = "file_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "files_seq_gen")
    private Long id;

    @Column(name = "file_name")
    private String name;

    @Column(name = "file_path")
    private String path;

    @Column(name = "file_size")
    private Integer size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private User owner;

    @Column(name = "create_date")
    private Date created;

    @Column(name = "update_date")
    private Date updated;

    @Column(name = "is_folder")
    private Boolean isFolder;

    @Column(name = "extension")
    private String extension;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "parent_file_id")
    private File parent;

    @Transient
    private String type;

    public File() {

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

    public File getParent() {
        return parent;
    }

    public void setParent(File parent) {
        this.parent = parent;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getId() {
        return id;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setFolder(Boolean isFolder) {
        this.isFolder = isFolder;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public Boolean isFolder() {
        return isFolder;
    }

    public String getType() {
        if (path != null && path.contains(".")) {
            return URLConnection.guessContentTypeFromName(path);
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        File file = (File) o;

        return new EqualsBuilder().append(id, file.id).append(name, file.name).append(path, file.path)
                .append(size, file.size).append(owner, file.owner).append(created, file.created)
                .append(isFolder, file.isFolder).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(name).append(path).append(size).append(owner)
                .append(created).append(isFolder).toHashCode();
    }
}
