package org.auth.api.application.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.auth.api.infrastructure.model.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {
    @Value("${auth.jwt.token.secret}")
    private String JWT_SECRET;

    public String generateToken(UserModel user, Integer expiration) {
        var algorithm = generateAlgorithm();

        return generateConstructorJwt()
                .withIssuer("auth-api")
                .withSubject(user.getUsername())
                .withExpiresAt(getTimeExpiration(expiration))
                .sign(algorithm);

    }

    public String extractUser(String jwtToken) {
        var algorithm = generateAlgorithm();

        return JWT.require(algorithm)
                .withIssuer("auth-api")
                .build()
                .verify(jwtToken)
                .getSubject();
    }

    private Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(JWT_SECRET);
    }

    private JWTCreator.Builder generateConstructorJwt() {
        return JWT.create();
    }

    private Instant getTimeExpiration(Integer expiration) {
        return LocalDateTime.now()
                .plusSeconds(expiration)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
