package bitc.next502.team_prj.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MngDTO {

  private String restaurantId;
  private String businessId;
  private String businessPhone;
  private String businessName;
  private int resvId;
  private String storeName;
  private String resvDate;
  private String resvTime;
  private int headCount;
  private String status;


  private String userId;
  private String userEmail;
  private String name;
  private String phone;

}
