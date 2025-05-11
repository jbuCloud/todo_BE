package todo.todoapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoLoginResponse {
    private Long memberId;
    private String accessToken;
}
