package org.auth.api.infrastructure.helper;

import lombok.AllArgsConstructor;
import org.auth.api.core.entity.User;
import org.auth.api.infrastructure.model.UserModel;
import org.auth.api.infrastructure.payload.UserRecordDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HelperMapper {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User recordToEntityCore(UserRecordDTO dto) {
        return new User(
                null,
                dto.username(),
                encoder.encode(dto.password()),
                dto.roles()
        );
    }

    public UserModel entityCoreToModel(User user) {
        return new UserModel(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
        );
    }

    public User modelToEntityCore(UserModel userModel) {
        return new User(
                userModel.getId(),
                userModel.getUsername(),
                userModel.getPassword(),
                userModel.getRoles()
        );
    }

    public UserRecordDTO entityCoreToRecord(User user) {
        return UserRecordDTO
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }
}
