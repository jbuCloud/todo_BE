package todo.todoapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "todo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    private Long memberId;
    private Long categoryId;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Integer priority; // 1~3

    @Builder.Default                    // ✅ 여기에 추가!
    @Column(nullable = false)
    private Boolean completed = false; // ✅ 기본값 false 설정
}
