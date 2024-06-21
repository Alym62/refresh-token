package org.auth.api.infrastructure.gateway;

import lombok.AllArgsConstructor;
import org.auth.api.core.entity.User;
import org.auth.api.core.gateway.UserGateway;
import org.auth.api.infrastructure.helper.HelperMapper;
import org.auth.api.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class UserGatewayImpl implements UserGateway {
    private final UserRepository repository;
    private final HelperMapper mapper;

    @Override
    public User execute(User user) {
        var userSave = repository.save(mapper.entityCoreToModel(user));
        return mapper.modelToEntityCore(userSave);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username)
                .map(mapper::modelToEntityCore);
    }
}
