package com.group7.evr.enums;

public enum ComplaintStatus {
    PENDING("Pending"),
    RESOLVED("Resolved"),
    REJECTED("Rejected");

    private final String value;

    ComplaintStatus(String value) {
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
