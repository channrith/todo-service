package com.futureflowhome.todo_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ListNotFoundException.class)
    public ProblemDetail handleListNotFound(ListNotFoundException ex, HttpServletRequest request) {
        return ApiProblemDetails.of(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), "LIST_NOT_FOUND", request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        String detail = ex.getMessage() != null ? ex.getMessage() : "Invalid request";
        return ApiProblemDetails.of(HttpStatus.BAD_REQUEST, "Bad Request", detail, "BAD_REQUEST", request);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
        if (ex.getMessage() != null && ex.getMessage().contains("User ID not found in JWT")) {
            return ApiProblemDetails.of(
                    HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage(), "INVALID_TOKEN_SUBJECT", request);
        }
        String detail = ex.getMessage() != null ? ex.getMessage() : "Unexpected state";
        return ApiProblemDetails.of(
                HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", detail, "INTERNAL_ERROR", request);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnhandled(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception", ex);
        return ApiProblemDetails.of(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected error occurred",
                "INTERNAL_ERROR",
                request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {
        ResponseEntity<Object> response = super.handleExceptionInternal(ex, body, headers, statusCode, request);
        if (response.getBody() instanceof ProblemDetail pd) {
            Map<String, Object> props = pd.getProperties();
            if (props == null || !props.containsKey(ApiProblemDetails.PROPERTY_CODE)) {
                pd.setProperty(ApiProblemDetails.PROPERTY_CODE, defaultErrorCode(statusCode.value()));
            }
        }
        return response;
    }

    private static String defaultErrorCode(int status) {
        return switch (status) {
            case 400 -> "BAD_REQUEST";
            case 404 -> "NOT_FOUND";
            case 405 -> "METHOD_NOT_ALLOWED";
            case 406 -> "NOT_ACCEPTABLE";
            case 409 -> "CONFLICT";
            case 415 -> "UNSUPPORTED_MEDIA_TYPE";
            case 422 -> "UNPROCESSABLE_ENTITY";
            case 503 -> "SERVICE_UNAVAILABLE";
            default -> "HTTP_" + status;
        };
    }
}
