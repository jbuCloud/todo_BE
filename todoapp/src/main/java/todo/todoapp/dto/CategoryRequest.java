package todo.todoapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    private Long memberId;
    private String type;
    private String name;
    private String color;

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

}

