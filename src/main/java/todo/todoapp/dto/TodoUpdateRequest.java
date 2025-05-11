package todo.todoapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TodoUpdateRequest {
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Boolean completed;
    private Integer priority;
}
