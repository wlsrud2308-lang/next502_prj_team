package bitc.next502.team_prj.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NormalUserDTO {

    private String userId;
    private String userPw;
    private int userRole;
    private String userEmail;
    private String name;
    private String phone;
    private int age;
    private LocalDateTime createDate;
    private LocalDateTime lastLoginTime;
    private String userFavorite;
    private String status;
    private LocalDateTime deleteReserveDate;
}
