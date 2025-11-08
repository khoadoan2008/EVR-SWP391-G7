package com.group7.evr.enums;

public enum MaintenanceStatus {
    OPEN("Open"),
    IN_PROGRESS("InProgress"),
    CLOSED("Closed");

    private final String value;

    MaintenanceStatus(String value) {
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
