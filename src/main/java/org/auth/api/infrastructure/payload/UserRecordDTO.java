package org.auth.api.infrastructure.payload;

import lombok.Builder;
import org.auth.api.core.enums.Role;

import java.util.Set;

@Builder
public record UserRecordDTO(
        String username,
        String password,
        Set<Role> roles
) {
}
