package bitc.next502.team_prj.dto;

import lombok.Data;

@Data
public class BusinessUserDTO {

    private String restaurantId;
    private String businessId;
    private String businessPwd;
    private String businessName;
    private String businessPhone;
    private String businessNumber;

    public String getName() {
        return businessName;
    }
}
