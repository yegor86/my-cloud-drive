package org.odesamama.mcd.domain;

import org.odesamama.mcd.domain.enums.Permissions;

public class AclBuilder {

    private User user;
    private Group group;
    private Permissions permissions;

    public AclBuilder user(User user) {
        this.user = user;
        return this;
    }

    public AclBuilder group(Group group) {
        this.group = group;
        return this;
    }

    public AclBuilder permissions(Permissions permissions) {
        this.permissions = permissions;
        return this;
    }

    public Acl build() {
        Acl acl = new Acl();
        acl.setUser(user);
        acl.setGroup(group);
        acl.setPermissions(permissions);

        return acl;
    }
}
