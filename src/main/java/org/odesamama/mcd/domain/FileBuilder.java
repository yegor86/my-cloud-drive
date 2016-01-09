package org.odesamama.mcd.domain;

import org.odesamama.mcd.domain.enums.Permissions;

public class FileBuilder {

    private User owner;
    private String name;
    private String path;
    private File parent;
    private Integer size;
    private Boolean isFolder;
    private String extension;
    private Permissions permissions;

    public FileBuilder owner(User owner) {
        this.owner = owner;
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

    public FileBuilder permissions(Permissions permissions) {
        this.permissions = permissions;
        return this;
    }

    public File build() {
        File file = new File();
        file.setOwner(owner);
        file.setName(name);
        file.setPath(path);
        file.setParent(parent);
        file.setSize(size);
        file.setFolder(isFolder);
        file.setExtension(extension);
        file.setPermissions(permissions);
        return file;
    }

}
