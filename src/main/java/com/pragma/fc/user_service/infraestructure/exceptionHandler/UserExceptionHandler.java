package com.pragma.fc.user_service.infraestructure.exceptionHandler;

import com.pragma.fc.user_service.domain.exception.UserAlreadyExistsException;
import com.pragma.fc.user_service.domain.exception.UserUnderageException;
import com.pragma.fc.user_service.infraestructure.exception.RestaurantNotFoundException;
import com.pragma.fc.user_service.infraestructure.exception.RoleNotFoundException;
import com.pragma.fc.user_service.infraestructure.exception.UserNotFoundException;
import com.pragma.fc.user_service.infraestructure.exception.WorkerAssignmentFailedException;
import com.pragma.fc.user_service.infraestructure.input.rest.dto.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(UserAlreadyExistsException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(UserUnderageException.class)
    public ResponseEntity<ApiError> handleUserUnderage(UserUnderageException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiError> handleRoleNotFound(RoleNotFoundException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(WorkerAssignmentFailedException.class)
    public ResponseEntity<ApiError> handleWorkerAssignmentFailed(WorkerAssignmentFailedException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ApiError> handleRestaurantNotFound(RestaurantNotFoundException ex, WebRequest request) {
        return ErrorUtils.buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }
}
