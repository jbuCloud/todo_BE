package todo.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import todo.todoapp.entity.Todo;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByMemberId(Long memberId);
}
