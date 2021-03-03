package com.kurlabo.backend.model;

import lombok.Getter;

@Getter
public enum MemberRole {
    Member("ROLE_MEMBER");

    private final String roleName;

    MemberRole(String roleName){
        this.roleName = roleName;
    }

}
