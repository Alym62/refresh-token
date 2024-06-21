package org.auth.api.core.usecases.create;

import org.auth.api.core.entity.User;

public interface CreateUserUseCase {
    User create(User user);
}
