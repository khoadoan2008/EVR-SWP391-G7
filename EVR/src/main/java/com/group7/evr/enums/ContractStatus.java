package com.group7.evr.enums;

public enum ContractStatus {
    ACTIVE("Active"),
    COMPLETED("Completed"),
    VIOLATED("Violated");

    private final String value;

    ContractStatus(String value) {
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
