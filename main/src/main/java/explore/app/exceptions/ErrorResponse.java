package explore.app.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
public class ErrorResponse {

    private final List<String> errors;
    private final String message;
    private final String reason;
    private final String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp;

    public ErrorResponse(RuntimeException ex, String reason, HttpStatus status) {
        this.errors = List.of(RuntimeException.class.toString());
        this.message = ex.getMessage();
        this.reason = reason;
        this.status = status.toString();
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(ConstraintViolationException ex, String reason, HttpStatus status) {
        this.errors = List.of(ConstraintViolationException.class.toString());
        this.message = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList()
                .get(0);
        this.reason = reason;
        this.status = status.toString();
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(MethodArgumentNotValidException ex, String reason, HttpStatus status) {
        this.errors = List.of(MethodArgumentNotValidException.class.toString());
        this.message = Objects.requireNonNull(ex.getBindingResult()
                        .getFieldError())
                .getDefaultMessage();
        this.reason = reason;
        this.status = status.toString();
        this.timestamp = LocalDateTime.now();
    }
}
