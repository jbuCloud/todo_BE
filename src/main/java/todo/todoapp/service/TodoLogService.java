package todo.todoapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import todo.todoapp.dto.TodoLogRequest;
import todo.todoapp.entity.TodoLog;
import todo.todoapp.repository.TodoLogRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoLogService {

    private final TodoLogRepository todoLogRepository;

    public TodoLog createLog(TodoLogRequest request) {
        TodoLog log = TodoLog.builder()
                .todoId(request.getTodoId())
                .memberId(request.getMemberId())
                .date(request.getDate())
                .completed(request.getCompleted())
                .build();
        return todoLogRepository.save(log);
    }

    public List<TodoLog> getLogsByMemberId(Long memberId) {
        return todoLogRepository.findAllByMemberId(memberId);
    }
}
