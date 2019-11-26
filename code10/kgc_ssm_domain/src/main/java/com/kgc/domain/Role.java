package com.kgc.domain;


import java.util.List;

public class Role {
    private String id;
    private String roleName;
    private String desc;
    private List<Permission> permissions;
    private List<UserInfo> users;

    public Role() {
    }

    public Role(String id, String roleName, String desc, List<Permission> permissions, List<UserInfo> users) {
        this.id = id;
        this.roleName = roleName;
        this.desc = desc;
        this.permissions = permissions;
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", roleName='" + roleName + '\'' +
                ", desc='" + desc + '\'' +
                ", permissions=" + permissions +
                ", users=" + users +
                '}';
    }
}
