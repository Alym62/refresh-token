package org.auth.api.core.gateway;

import org.auth.api.core.entity.User;

import java.util.Optional;

public interface UserGateway {
    User execute(User user);
    Optional<User> findByUsername(String username);
}
