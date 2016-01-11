package org.odesamama.mcd.domain;

import java.util.UUID;

public class FileBuilder {

    private String fileUid = UUID.randomUUID().toString();;
    private User owner;
    private Group group;
    private String name;
    private String path;
    private File parent;
    private Integer size;
    private Boolean isFolder;
    private String extension;

    public FileBuilder fileUid(String fileUid) {
        this.fileUid = fileUid;
        return this;
    }

    public FileBuilder owner(User owner) {
        this.owner = owner;
        return this;
    }

    public FileBuilder group(Group group) {
        this.group = group;
        return this;
    }

    public FileBuilder name(String name) {
        this.name = name;
        return this;
    }

    public FileBuilder path(String path) {
        this.path = path;
        return this;
    }

    public FileBuilder parent(File parent) {
        this.parent = parent;
        return this;
    }

    public FileBuilder size(Integer size) {
        this.size = size;
        return this;
    }

    public FileBuilder isFolder(Boolean isFolder) {
        this.isFolder = isFolder;
        return this;
    }

    public FileBuilder extension(String extension) {
        this.extension = extension;
        return this;
    }

    public File build() {
        File file = new File();
        file.setFileUid(fileUid);
        file.setOwner(owner);
        file.setName(name);
        file.setPath(path);
        file.setParent(parent);
        file.setSize(size);
        file.setFolder(isFolder);
        file.setExtension(extension);
        file.setGroup(group);
        return file;
    }

}
