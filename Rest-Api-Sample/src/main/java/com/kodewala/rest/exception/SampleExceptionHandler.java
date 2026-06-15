package com.kodewala.rest.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the REST API.
 *
 * @RestControllerAdvice combines @ControllerAdvice and @ResponseBody,
 * allowing this class to intercept exceptions thrown by any
 * @RestController across the entire application and return
 * structured JSON error responses instead of default error pages.
 *
 * Layer Role : Cross-Cutting Concern (Exception Handling)
 * Scope      : Application-wide — applies to all REST controllers
 */
@RestControllerAdvice
public class SampleExceptionHandler {

    /**
     * Handles {@link UserNotFoundException} thrown when a requested user
     * does not exist in the database.
     *
     * @ExceptionHandler(UserNotFoundException.class) registers this method
     * as the handler specifically for UserNotFoundException.
     *
     * Returns a structured JSON response with:
     *   - "status code" : custom application error code "CNF102"
     *   - "message"     : the exception's detail message
     *
     * HTTP Status : 200 OK
     * Note: Consider using HttpStatus.NOT_FOUND (404) for semantically
     *       correct REST error responses.
     *
     * @param exception The caught UserNotFoundException instance
     * @return ResponseEntity containing the error map as JSON body
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleNotFoundException(UserNotFoundException exception) {

        System.out.println("SampleExceptionHandler.handleNotFoundException()");

        Map<String, Object> response = new HashMap<>();
        response.put("status code", "CNF102");
        response.put("message", exception.getMessage());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Fallback handler for all other unhandled exceptions.
     *
     * @ExceptionHandler(Exception.class) catches any exception not handled
     * by a more specific @ExceptionHandler method above.
     * Acts as a safety net to prevent unhandled exceptions from
     * leaking stack traces to the client.
     *
     * Returns the exception message directly as a plain string.
     * Note: Consider returning a structured ResponseEntity with
     *       HttpStatus.INTERNAL_SERVER_ERROR (500) in production.
     *
     * @param exception The caught Exception instance
     * @return Plain text error message from the exception
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception) {

        System.out.println("SampleExceptionHandler.handleException()");

        return exception.getMessage();
    }
}