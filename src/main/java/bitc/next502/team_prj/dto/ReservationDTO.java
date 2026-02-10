package bitc.next502.team_prj.dto;

import lombok.Data;

@Data
public class ReservationDTO {

    private String reservationId;
    private String restaurantId;
    private String userId;
    private String reservationDate;
    private String reservationTime;
    private String reservationState;
    private String countPeople;
    private String restaurantName;
    private String userName;
    private String userTel;

}
