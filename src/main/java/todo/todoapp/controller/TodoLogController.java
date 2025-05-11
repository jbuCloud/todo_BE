package todo.todoapp.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todo.todoapp.dto.TodoLogRequest;
import todo.todoapp.entity.TodoLog;
import todo.todoapp.service.TodoLogService;

import java.util.List;

@SecurityRequirement(name = "bearerAuth") //
@RestController
@RequestMapping("/api/todos/logs")
@RequiredArgsConstructor
public class TodoLogController {

    private final TodoLogService todoLogService;

    @PostMapping
    public ResponseEntity<TodoLog> createLog(@RequestBody TodoLogRequest request) {
        return ResponseEntity.ok(todoLogService.createLog(request));
    }

    @GetMapping
    public ResponseEntity<List<TodoLog>> getLogs(@RequestParam Long memberId) {
        return ResponseEntity.ok(todoLogService.getLogsByMemberId(memberId));
    }
}
