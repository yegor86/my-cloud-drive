package org.odesamama.mcd.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by starnakin on 25.09.2015.
 */

@Entity
@Table(name ="files")
@NamedQueries({
        @NamedQuery(name = "File.getFileInfoByFilePathAndEmail", query = "from File where owner.userEmail = :email and filePath = :path"),
        @NamedQuery(name = "File.getFilesListForGivenFolder", query = "from File where parent = :parent")})
public class File {

    @Id
    @Column(name = "file_id")
    @SequenceGenerator(name = "files_seq_gen", sequenceName = "file_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "files_seq_gen")
    private Long id;

    @Column(name = "file_name")
    private String fileName;


    @Column(name ="file_path")
    private String filePath;

    @Column(name = "file_size")
    private Integer fileSize;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name ="owner_id")
    @JsonBackReference
    private User owner;

    @Column(name = "create_date")
    private Date created;

    @Column(name = "update_date")
    private Date updated;

    @Column(name ="is_folder")
    private Boolean isFolder;

    @Column(name="extension")
    private String extension;

    @ManyToOne(fetch=FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="parent_file_id")
    private File parent;

    public File(){

    }

    @PreUpdate
    public void preUpdate(){
        updated = new Date();
    }

    @PrePersist
    public void preCreate(){
        created = new Date();
        updated = new Date();
    }

    public File(User user, String fileName, String filePath, File parentId, Integer fileSize, Boolean isFolder){
        this.owner = user;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.parent = parentId;
        this.isFolder = isFolder;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        return new EqualsBuilder()
                .append(id, file.id)
                .append(fileName, file.fileName)
                .append(filePath, file.filePath)
                .append(fileSize, file.fileSize)
                .append(owner, file.owner)
                .append(created, file.created)
                .append(isFolder, file.isFolder)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(fileName)
                .append(filePath)
                .append(fileSize)
                .append(owner)
                .append(created)
                .append(isFolder)
                .toHashCode();
    }
}
