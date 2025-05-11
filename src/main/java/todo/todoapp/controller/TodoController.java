package todo.todoapp.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import todo.todoapp.dto.TodoRequest;
import todo.todoapp.dto.TodoUpdateRequest;
import todo.todoapp.entity.Member;
import todo.todoapp.entity.Todo;
import todo.todoapp.service.TodoService;

import java.util.List;

@SecurityRequirement(name = "bearerAuth") //
@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody TodoRequest request,
                                           @AuthenticationPrincipal Member member) {
        System.out.println("🔥 인증된 사용자 memberId: " + member.getMemberId());
        request.setMemberId(member.getMemberId()); // 인증된 사용자 ID 설정
        Todo saved = todoService.createTodo(request);
        return ResponseEntity.ok(saved);
    }

    // ✅ 할 일 목록 조회
    @GetMapping
    public ResponseEntity<List<Todo>> getTodos(@AuthenticationPrincipal Member member) {
        List<Todo> todos = todoService.getTodosByMemberId(member.getMemberId());
        return ResponseEntity.ok(todos);
    }

    // ✅ 할 일 수정
    @PutMapping("/{todoId}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long todoId,
                                           @RequestBody TodoUpdateRequest request) {
        return ResponseEntity.ok(todoService.updateTodo(todoId, request));
    }

    // ✅ 할 일 삭제
    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.noContent().build();
    }


}
