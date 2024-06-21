package org.auth.api.application.services;

import lombok.AllArgsConstructor;
import org.auth.api.core.entity.User;
import org.auth.api.infrastructure.gateway.UserGatewayImpl;
import org.auth.api.infrastructure.helper.HelperMapper;
import org.auth.api.infrastructure.model.UserModel;
import org.auth.api.infrastructure.payload.UserRecordDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserGatewayImpl gateway;
    private final HelperMapper mapper;

    @Transactional
    public UserRecordDTO create(UserRecordDTO userRecordDTO) {
        var mapperEntity = gateway.execute(
                new User(
                        null,
                        userRecordDTO.username(),
                        userRecordDTO.password(),
                        userRecordDTO.roles()
                )
        );
        return mapper.entityCoreToRecord(mapperEntity);
    }

    @Override
    public UserModel loadUserByUsername(String username) throws UsernameNotFoundException {
        return gateway.findByUsername(username)
                .map(mapper::entityCoreToModel)
                .orElseThrow(() -> new UsernameNotFoundException("Esse usuário não existe na base de dados: " + username));
    }
}
