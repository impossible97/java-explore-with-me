package ru.practicum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<ApiError> handleValidationException(final ValidationException e) {
        ApiError apiError = new ApiError();
        apiError.setMessage("Сведения об ошибке");
        apiError.setReason(e.getMessage());
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        List<String> errors = new ArrayList<>();
        Arrays.stream(e.getStackTrace()).map(stackTraceElement -> errors.add(stackTraceElement.getClassName())).collect(Collectors.toList());
        apiError.setErrors(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
