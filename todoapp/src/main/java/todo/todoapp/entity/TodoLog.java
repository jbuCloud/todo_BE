package todo.todoapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "todo_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long todoId;
    private Long memberId;
    private LocalDateTime date;
    private Boolean completed;
}
