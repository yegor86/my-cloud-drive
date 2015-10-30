package org.odesamama.mcd.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by starnakin on 25.09.2015.
 */

@Entity
@Table(name ="files")
public class File {

    @Id
    @Column(name = "file_id")
    @SequenceGenerator(name = "files_seq_gen", sequenceName = "file_id_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "files_seq_gen")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Integer fileSize;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name ="owner_id")
    @JsonBackReference
    private User owner;

    @Column(name = "create_date")
    @JsonIgnore
    private Date created;

    @Column(name = "update_date")
    @JsonIgnore
    private Date updated;

    @Column(name ="is_folder")
    private Boolean isDirectory;

    public File(){

    }

    public File(User user, String fileName, String path, Integer fileSize, Boolean isDirectory){
        this.owner = user;
        this.fileName = fileName;
        this.filePath = path;
        this.fileSize = fileSize;
        this.isDirectory = isDirectory;
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

    public Boolean getIsDirectory() {
        return isDirectory;
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
                .append(isDirectory, file.isDirectory)
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
                .append(isDirectory)
                .toHashCode();
    }
}
