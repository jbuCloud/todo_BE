package todo.todoapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TodoRequest {
    private Long memberId;
    private Long categoryId;
    private String title;
    private String description;
    private LocalDateTime dueDate;

    private Integer priority;
}
