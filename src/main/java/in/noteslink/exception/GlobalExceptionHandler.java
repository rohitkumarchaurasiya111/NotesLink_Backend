package in.noteslink.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

// This class is Basically for Exception Handling purpose so that whenever any error occurs
// the frontend will get a specific crafted Message
//
// Centralized error handler.
// Instead of sending raw stack traces to the frontend,
// app converts errors into clean JSON responses.

@RestControllerAdvice
// This is a global interceptor, it means it watches all controllers
// and catches exceptions thrown anywhere and prevents ugly stack traces.
public class GlobalExceptionHandler {

    // --------------------------------------------------------
    // Handles ResponseStatusException (commonly thrown manually in service layer)
    // Example: throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found");
    // --------------------------------------------------------
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {

        Map<String, Object> body = new HashMap<>();

        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());

        body.put("timestamp", Instant.now().toString());
        body.put("status", ex.getStatusCode().value());
        body.put("error", status.getReasonPhrase());
        body.put("message", ex.getReason());

        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    // --------------------------------------------------------
    // Handles custom BadRequestException
    // While @ResponseStatus sets the status, it does NOT:
    // - Format the response body
    // - Standardize error responses
    // That’s why we handle it here.
    // --------------------------------------------------------
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex) {

        Map<String, Object> body = new HashMap<>();

        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(body);
    }

    // --------------------------------------------------------
    // Handles IllegalArgumentException
    // Commonly thrown for invalid method arguments
    // --------------------------------------------------------
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {

        Map<String, Object> body = new HashMap<>();

        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(body);
    }

    // --------------------------------------------------------
    // Handles @Valid validation errors
    // Triggered when DTO validation fails
    // --------------------------------------------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, Object> body = new HashMap<>();

        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());

        // Pick first validation message (clean & frontend-friendly)
        String msg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst()
                .orElse(ex.getMessage());

        body.put("message", msg);

        return ResponseEntity.badRequest().body(body);
    }

    // --------------------------------------------------------
    // Catch-all handler (LAST RESORT)
    // Prevents internal stack traces from leaking to frontend
    // --------------------------------------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {

        Map<String, Object> body = new HashMap<>();

        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        body.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
