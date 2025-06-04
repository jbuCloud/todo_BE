package todo.todoapp.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import todo.todoapp.dto.member.SignupInitResponse;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(HttpServletRequest request, IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 400,
                "error", "Bad Request",
                "message", e.getMessage(),
                "path", request.getRequestURI()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(HttpServletRequest request, Exception e) {
        return ResponseEntity.status(500).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 500,
                "error", "Internal Server Error",
                "message", e.getMessage(),
                "path", request.getRequestURI()
        ));
    }
    @ExceptionHandler(SignupRequiredException.class)
    public ResponseEntity<?> handleGeneralException(HttpServletRequest request, SignupRequiredException e) {
        return ResponseEntity.status(401).body(new SignupInitResponse(e));
    }

}
