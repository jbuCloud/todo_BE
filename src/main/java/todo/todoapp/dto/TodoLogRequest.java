package todo.todoapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TodoLogRequest {
    private Long todoId;
    private Long memberId;
    private LocalDateTime date;
    private Boolean completed;
}
