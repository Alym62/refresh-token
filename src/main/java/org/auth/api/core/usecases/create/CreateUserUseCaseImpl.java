package org.auth.api.core.usecases.create;

import org.auth.api.core.entity.User;
import org.auth.api.core.gateway.UserGateway;

public class CreateUserUseCaseImpl implements CreateUserUseCase{
    private final UserGateway gateway;

    public CreateUserUseCaseImpl(UserGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public User create(User user) {
        return gateway.execute(user);
    }
}
