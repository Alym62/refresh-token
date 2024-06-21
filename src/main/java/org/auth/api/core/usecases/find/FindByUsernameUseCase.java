package org.auth.api.core.usecases.find;

import org.auth.api.core.entity.User;

import java.util.Optional;

public interface FindByUsernameUseCase {
    Optional<User> findByUsername(String username);
}
