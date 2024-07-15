package work.task.services;

import work.task.payload.LoginRequest;

import java.util.Map;
import java.util.Optional;

public interface AuthenticationService {
    Optional<Map<String, Object>> login(LoginRequest loginRequest);
}
