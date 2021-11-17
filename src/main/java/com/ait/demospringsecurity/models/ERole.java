package com.ait.demospringsecurity.models;

public enum ERole {
	ROLE_USER("ROLE_USER"),
    ROLE_MODERATOR("ROLE_MODERATOR"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_STUDENT("ROLE_STUDENT");

    private final String value;

    ERole(String value) {
        this.value = value;
    }
    //
    public String getValue() {
        return this.value;
    }
}
