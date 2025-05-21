package todo.todoapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TodoDateUpdateRequest {
    private LocalDateTime dueDate;
}
