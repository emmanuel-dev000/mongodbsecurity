package com.pangan.mongodbsecurity.model;

import org.springframework.data.annotation.Id;

public class Role {
    @Id
    private String id;
    private String roleName;

    public Role() {
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
}
