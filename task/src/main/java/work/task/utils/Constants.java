package work.task.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String JWT_SUBJECT = "User Details";

    public static final String JWT_CLAIM_USERNAME = "username";

    public static final String JWT_ISSUER = "spring-app/core-api/ams";

    public static  final  String GRANTED_AUTHORITY_FIRST_KEY  = "ROLE_";

    public static  final  String GRANTED_AUTHORITY_SECOND_KEY  = "permission:";

    public static  final String APP_USER_DTO = "app.user.dto";

    public static  final String SECURITY_ATTRIBUTE_TOKEN ="token";

}