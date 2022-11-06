package ru.practicum.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@ToString
public class ErrorResponse {

    private final String message;
    private final String reason;
    private final String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp;

    public ErrorResponse(RuntimeException ex, String reason, HttpStatus status) {
        this.message = ex.getMessage();
        this.reason = reason;
        this.status = status.toString();
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(ConstraintViolationException ex, String reason, HttpStatus status) {
        this.message = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList())
                .get(0);
        this.reason = reason;
        this.status = status.toString();
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(MethodArgumentNotValidException ex, String reason, HttpStatus status) {
        this.message = Objects.requireNonNull(ex.getBindingResult()
                        .getFieldError())
                .getDefaultMessage();
        this.reason = reason;
        this.status = status.toString();
        this.timestamp = LocalDateTime.now();
    }
}
