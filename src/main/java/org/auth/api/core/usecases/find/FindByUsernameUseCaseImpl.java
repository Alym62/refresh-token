package org.auth.api.core.usecases.find;

import org.auth.api.core.entity.User;
import org.auth.api.core.gateway.UserGateway;

import java.util.Optional;

public class FindByUsernameUseCaseImpl implements FindByUsernameUseCase {
    private final UserGateway gateway;

    public FindByUsernameUseCaseImpl(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return gateway.findByUsername(username);
    }
}
