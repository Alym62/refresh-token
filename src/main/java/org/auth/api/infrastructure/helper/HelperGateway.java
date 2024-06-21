package org.auth.api.infrastructure.helper;

import org.auth.api.core.gateway.UserGateway;
import org.auth.api.core.usecases.create.CreateUserUseCase;
import org.auth.api.core.usecases.create.CreateUserUseCaseImpl;
import org.auth.api.core.usecases.find.FindByUsernameUseCase;
import org.auth.api.core.usecases.find.FindByUsernameUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelperGateway {
    @Bean
    public CreateUserUseCase createUserUseCase(UserGateway gateway) {
        return new CreateUserUseCaseImpl(gateway);
    }

    @Bean
    public FindByUsernameUseCase findByUsernameUseCase(UserGateway gateway) {
        return new FindByUsernameUseCaseImpl(gateway);
    }
}
