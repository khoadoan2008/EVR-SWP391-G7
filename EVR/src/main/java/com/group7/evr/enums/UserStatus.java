package com.group7.evr.enums;

public enum UserStatus {
    ACTIVE("Active"),
    SUSPENDED("Suspended"),
    DELETED("Deleted");

    private final String value;

    UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
