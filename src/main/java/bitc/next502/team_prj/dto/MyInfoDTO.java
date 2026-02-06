package bitc.next502.team_prj.dto;

import lombok.Data;

@Data
public class MyInfoDTO {
    private String email;
    private String phone;  // XML의 AS phone과 매칭
    private int age;
    private String name;
    private String grade;  // XML의 AS grade와 매칭
    private String pw;
    private String userId;
}