package org.auth.api.infrastructure.payload;

import lombok.Builder;

@Builder
public record JWTTokenResponseDTO(
        String token,
        String RefreshToken
) {
}
