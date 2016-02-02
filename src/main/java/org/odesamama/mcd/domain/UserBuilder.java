package org.odesamama.mcd.domain;

import java.util.List;

public class UserBuilder {

    private Long userId;
    private String userUid;
    private String userName;
    private String lastName;
    private String userEmail;
    private Group group;
    private List<File> fileList;

    public UserBuilder userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public UserBuilder userUid(String userUid) {
        this.userUid = userUid;
        return this;
    }

    public UserBuilder userName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder userEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public UserBuilder group(Group group) {
        this.group = group;
        return this;
    }

    public UserBuilder fileList(List<File> fileList) {
        this.fileList = fileList;
        return this;
    }

    public User build() {
        User user = new User();
        user.setUserId(userId);
        user.setUserUid(userUid);
        user.setUserName(userName);
        user.setLastName(lastName);
        user.setUserEmail(userEmail);
        user.setGroup(group);
        user.setFileList(fileList);
        return user;
    }

}
