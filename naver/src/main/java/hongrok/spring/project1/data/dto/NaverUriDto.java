package hongrok.spring.project1.data.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class NaverUriDto {

    private String message;

    private String code;

    private Result result;

    @Getter
    @Setter
    //naver의 반환 형식에 맞춰주기 위한 설정
    public static class Result {

        private String hash;

        private String url;

        private String orgUrl;
    }
}
