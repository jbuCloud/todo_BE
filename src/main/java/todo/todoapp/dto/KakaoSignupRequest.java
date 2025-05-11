package todo.todoapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoSignupRequest {
    private Long kakaoId;
    private String email;
    private String nickname;
    private String profileUrl;
    private String introText;
}