package bitc.next502.team_prj.dto;

import lombok.Data;

@Data
public class MyInfoDTO {
    private String userId;
    private String name;     // user_name
    private String email;    // user_email
    private String phone;    // user_tel
    private int age;         // user_age
    private String password;
    private String grade;
}