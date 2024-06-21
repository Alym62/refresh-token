package org.auth.api.infrastructure.payload;

import org.auth.api.core.enums.Role;

import java.util.Set;

public record UserRecordDTO(
        String username,
        String password,
        Set<Role> roles
) {
}
