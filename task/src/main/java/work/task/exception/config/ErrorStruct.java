package work.task.exception.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Represents the structure of an error response.
 * <p>
 * This class includes details such as the error code, HTTP status, timestamp, and error message.
 * </p>
 */
@Data
@AllArgsConstructor
@Builder
public class ErrorStruct {

    @JsonProperty("errorCode")
    private int code;

    @JsonProperty("httpStatus")
    private HttpStatus httpStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @JsonProperty("localDateTime")
    private LocalDateTime localDateTime;

    @JsonProperty("errorMessage")
    private String message;

    /**
     * Constructs an ErrorStruct with the given HTTP status and error message.
     * The error code is derived from the HTTP status, and the timestamp is set to the current time.
     *
     * @param httpStatus the HTTP status of the error
     * @param error the error message
     */
    public ErrorStruct(HttpStatus httpStatus, String error) {
        this.httpStatus = httpStatus;
        this.message = error;
        this.localDateTime = LocalDateTime.now();
        this.code = httpStatus.value();
    }

    /**
     * Creates an ErrorStructBuilder with default local date and time set to the current time.
     *
     * @param httpStatus the HTTP status of the error
     * @param errorMessage the error message
     * @return an ErrorStructBuilder with default local date and time
     */
    public static ErrorStructBuilder withDefaultLocalDateTime(HttpStatus httpStatus, String errorMessage) {
        return ErrorStruct.builder()
                .httpStatus(httpStatus)
                .localDateTime(LocalDateTime.now())
                .message(errorMessage)
                .code(httpStatus.value());
    }
}
