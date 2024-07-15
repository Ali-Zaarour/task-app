package work.task.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * Utility class for handling JWT operations such as token generation and validation.
 */
@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expireMonth}")
    private int expireMonth;

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails the user details
     * @return the generated JWT token
     * @throws IllegalArgumentException if the token generation fails due to invalid arguments
     * @throws JWTCreationException     if there is an error during the creation of the JWT
     */
    public String generateJWTToken(UserDetails userDetails) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject(Constants.JWT_SUBJECT)
                .withClaim(Constants.JWT_CLAIM_USERNAME, userDetails.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(Date.from(LocalDateTime.now().plusMonths(expireMonth).toInstant(ZoneOffset.UTC)))
                .withIssuer(Constants.JWT_ISSUER)
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * Validates the given JWT token and retrieves the subject (username).
     *
     * @param token the JWT token
     * @return the subject (username) from the token
     * @throws JWTVerificationException if the token validation fails
     */
    public String validateJWTTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(Constants.JWT_SUBJECT)
                .withIssuer(Constants.JWT_ISSUER)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim(Constants.JWT_CLAIM_USERNAME).asString();
    }
}