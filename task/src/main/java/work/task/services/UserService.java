package work.task.services;


import work.task.dto.AppUserDTO;
import work.task.payload.CreateUserRequest;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<AppUserDTO> createUser(CreateUserRequest createUserRequest, UUID principalId);
}
