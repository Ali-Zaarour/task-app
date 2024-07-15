package work.task.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Data
@Builder
public class CreateUserRequest {

    @NonNull
    @NotEmpty(message = "username.is.required")
    @Size(min = 2, max =200, message = "username.must.be.between.5.and.200.characters")
    private String username;

    @NonNull
    @NotEmpty(message = "password.is.required")
    @Size(min = 10, message = "password.must.be.at.least.10.characters")
    private String password;

    @NotNull
    private UUID organizationId;

}
