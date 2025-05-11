package todo.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import todo.todoapp.entity.TodoLog;

import java.util.List;
import java.util.Optional;

public interface TodoLogRepository extends JpaRepository<TodoLog, Long> {
    List<TodoLog> findAllByMemberId(Long memberId);
    Optional<TodoLog> findByTodoId(Long todoId);

}
