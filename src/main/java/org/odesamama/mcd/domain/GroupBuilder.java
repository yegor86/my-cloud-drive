package org.odesamama.mcd.domain;

public class GroupBuilder {

    private Long groupId;
    private String groupName;
    private User owner;

    public GroupBuilder groupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public GroupBuilder groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public GroupBuilder owner(User owner) {
        this.owner = owner;
        return this;
    }

    public Group build() {
        Group group = new Group();
        group.setGroupId(groupId);
        group.setGroupName(groupName);
        group.setOwner(owner);
        return group;
    }
}
