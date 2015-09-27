package org.odesamama.mcd.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;

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
    private Long fileSize;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name ="owner_id")
    @JsonBackReference
    private User owner;

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

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
                .toHashCode();
    }
}
