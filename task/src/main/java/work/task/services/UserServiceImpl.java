package work.task.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import work.task.dto.AppUserDTO;
import work.task.entity.AppUser;
import work.task.entity.OrganizationSide;
import work.task.payload.CreateUserRequest;
import work.task.repositories.AppUserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder argon2PasswordEncoder;

    @Autowired
    public UserServiceImpl(AppUserRepository appUserRepository, PasswordEncoder argon2PasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.argon2PasswordEncoder = argon2PasswordEncoder;
    }

    @Override
    public Optional<AppUserDTO> createUser(CreateUserRequest createUserRequest, UUID principalId) {
        //check if user already exist with the same username provided by the administrator
        var exist = appUserRepository.existsByUsername(createUserRequest.getUsername());
        if(!exist) {
        // create a new client
            var newAppUser = AppUser.builder()
                    .username(createUserRequest.getUsername())
                    .password(argon2PasswordEncoder.encode(createUserRequest.getPassword()))
                    .organizationSide(OrganizationSide.builder().id(createUserRequest.getOrganizationId()).build())
                    .createdBy(AppUser.builder().id(principalId).build())
                    .build();
            var createdAppUser = appUserRepository.save(newAppUser);
            return Optional.of(new AppUserDTO(createdAppUser.getId(), createdAppUser.getUsername()));
        }
        return Optional.empty();
    }
}
