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

    @Column(name = "member_id") // 🔧 물리 컬럼 이름 명시
    private Long memberId;

    @Column(name = "category_id") // 🔧 물리 컬럼 이름 명시
    private Long categoryId;

    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Integer priority; // 1~3

    @Builder.Default
    @Column(nullable = false)
    private Boolean completed = false;

    // ✅ 연관관계 필드 (읽기 전용)
    @ManyToOne
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;
}
