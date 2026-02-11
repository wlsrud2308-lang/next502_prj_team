package bitc.next502.team_prj.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BusinessUserDTO {

    private String restaurantId;
    private String businessId;
    private String businessPwd;
    private String businessName;
    private String businessPhone;
    private String businessNumber;
    private String status;
    private LocalDateTime deleteReserveDate;

    public String getName() {
        return businessName;
    }
}
