package bitc.next502.team_prj.dto;

import lombok.Data;

@Data
public class MyResvDTO {
    private int resvId;
    private String storeName;
    private String resvDate;
    private String resvTime;
    private int headCount;
    private String status;
    private int isReviewed;

}