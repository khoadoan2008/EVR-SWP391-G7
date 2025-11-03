package com.group7.evr.enums;

public enum UserRole {
    CUSTOMER("Customer"),
    STAFF("Staff"),
    ADMIN("Admin");

    private final String value;

    UserRole(String value) {
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
