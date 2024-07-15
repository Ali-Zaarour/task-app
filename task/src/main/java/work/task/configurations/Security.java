package work.task.configurations;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import work.task.exception.config.ExceptionMessage;
import work.task.utils.JWTFilter;

/**
 * <p>For more information about this topic visit spring documentation.</p>
 * <p>
 * 1. <a href="https://docs.spring.io/spring-security/reference/index.html">Spring security</a>
 */


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Security {

    private static final String[] WHITE_LIST_URL = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/unsecured/authentication/**"
    };

    private final JWTFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public Security(JWTFilter jwtFilter, AuthenticationProvider authenticationProvider) {
        this.jwtFilter = jwtFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // todo: activate later
                .httpBasic(AbstractHttpConfigurer::disable)// todo: activate later
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(this.authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exHd -> exHd.authenticationEntryPoint(
                        // Rejecting request as unauthorized when entry point is reached
                        // If this point is reached it means that the current request requires authentication
                        // and no JWT token was found attached to the Authorization header of the current request.
                        (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ExceptionMessage.UNAUTHORIZED_ACTION)));
        return httpSecurity.build();
    }
}
