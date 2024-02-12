package ru.practicum.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiError {

    String message;
    String reason;
    HttpStatus status;
    List<String> errors;
    LocalDateTime timestamp = LocalDateTime.now();
}
