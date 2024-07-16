package work.task.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import work.task.exception.config.ExceptionMessage;
import work.task.payload.LoginRequest;
import work.task.services.AuthenticationService;
import work.task.utils.Constants;

@Controller
@RequestMapping("/unsecured")
public class UnsecureController {

    private final AuthenticationService authenticationService;

    @Autowired
    public UnsecureController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/authentication")
    public String login() {
        return "login";
    }

    @PostMapping("/authentication")
    public String authenticate(@Valid @RequestBody LoginRequest loginRequest, Model model) {
        return authenticationService.login(loginRequest).map(data -> {
            model.addAttribute(Constants.SECURITY_ATTRIBUTE_TOKEN, data.get(Constants.SECURITY_ATTRIBUTE_TOKEN));
            model.addAttribute(Constants.APP_USER_DTO, data.get(Constants.APP_USER_DTO));
            return "home";
        }).orElseThrow(() -> new AccessDeniedException(ExceptionMessage.ACCESS_DENIED));
    }

}
