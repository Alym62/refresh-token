package org.auth.api.application.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.auth.api.core.entity.User;
import org.auth.api.infrastructure.model.UserModel;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;

@Service
public class JwtService {
    private static final String JWT_SECRET = "W+jX#P9o2}40%#J#7e[qGZ)Ps1g6A5@>PVSoDIH+ls87zrd(Z$";
    private static final Integer EXPIRES_IN = 599;

    public HashMap<String, Object> generateToken(UserModel user) {
        var algorithm = generateAlgorithm();

        var token = generateConstructorJwt()
                .withIssuer("auth-api")
                .withSubject(user.getUsername())
                .withExpiresAt(getTimeExpiration())
                .sign(algorithm);

        var response = new HashMap<String, Object>();
        response.put("token", token);
        response.put("expires_in", getTimeExpiration());

        return response;
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

    private Instant getTimeExpiration() {
        return LocalDateTime.now().plusSeconds(EXPIRES_IN).toInstant(ZoneOffset.of("-03:00"));
    }
}
