package work.task.controllers;

import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.task.dto.AppUserDTO;
import work.task.entity.AppUser;
import work.task.utils.ExceptionMessage;
import work.task.payload.CreateUserRequest;
import work.task.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') &&  hasAuthority('permission:create')")
    public ResponseEntity<AppUserDTO> createUser(@Valid @RequestBody CreateUserRequest createUserRequest, @AuthenticationPrincipal @NonNull AppUser principalDetails) {
        var createdUser = userService.createUser(createUserRequest, principalDetails.getId());
        return createdUser.map(ResponseEntity::ok).orElseThrow(() -> new DataIntegrityViolationException(ExceptionMessage.DATA_INTEGRITY_VIOLATION));
    }
}
