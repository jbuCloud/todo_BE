package todo.todoapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import todo.todoapp.dto.TodoRequest;
import todo.todoapp.dto.TodoUpdateRequest;
import todo.todoapp.entity.Todo;
import todo.todoapp.entity.TodoLog;
import todo.todoapp.repository.TodoLogRepository;
import todo.todoapp.repository.TodoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    private final TodoLogRepository todoLogRepository;

    // 할 일 생성
    public Todo createTodo(TodoRequest request) {
        Todo todo = Todo.builder()
                .memberId(request.getMemberId())
                .categoryId(request.getCategoryId())
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .completed(false)
                .priority(request.getPriority())
                .build();

        Todo saved = todoRepository.save(todo);

        // ✅ 투두 로그 자동 생성
        TodoLog log = TodoLog.builder()
                .todoId(saved.getTodoId())
                .memberId(saved.getMemberId())
                .date(null)
                .completed(false)
                .build();
        todoLogRepository.save(log);

        return saved;
    }

    //할 일 목록 조회(회원 기준)
    public List<Todo> getTodosByMemberId(Long memberId) {
        return todoRepository.findAllByMemberId(memberId);
    }

    //할 일 수정 기능
    public Todo updateTodo(Long todoId, TodoUpdateRequest request) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 할 일을 찾을 수 없습니다."));

        if (request.getTitle() != null) todo.setTitle(request.getTitle());
        if (request.getDescription() != null) todo.setDescription(request.getDescription());
        if (request.getDueDate() != null) todo.setDueDate(request.getDueDate());
        if (request.getCompleted() != null) {
            todo.setCompleted(request.getCompleted());

            // ✅ 만약 상태가 "완료"라면 todo_log도 업데이트
            if (request.getCompleted().equals("완료")) {
                TodoLog log = todoLogRepository.findByTodoId(todoId)
                        .orElseThrow(() -> new IllegalArgumentException("로그 정보가 없습니다."));
                log.setCompleted(true);
                log.setDate(LocalDateTime.now());
                todoLogRepository.save(log);
            }
        }
        if (request.getPriority() != null) todo.setPriority(request.getPriority());

        return todoRepository.save(todo);
    }


    //할 일 삭제
    public void deleteTodo(Long todoId) {
        if (!todoRepository.existsById(todoId)) {
            throw new IllegalArgumentException("해당 할 일을 찾을 수 없습니다.");
        }
        todoRepository.deleteById(todoId);
    }


}

