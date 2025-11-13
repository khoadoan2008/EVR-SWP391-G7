package com.group7.evr.enums.converter;

import com.group7.evr.enums.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole attribute) {
        return attribute != null ? attribute.name() : null;
    }

    @Override
    public UserRole convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        String normalized = dbData.trim();
        try {
            return UserRole.valueOf(normalized.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return Arrays.stream(UserRole.values())
                    .filter(role -> role.getValue().equalsIgnoreCase(normalized))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unsupported user role: " + dbData, ex));
        }
    }
}


