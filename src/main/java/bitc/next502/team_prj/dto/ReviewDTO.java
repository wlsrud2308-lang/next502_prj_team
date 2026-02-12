package bitc.next502.team_prj.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private int reviewIdx;
    private String restaurantId;
    private int reservationId;
    private String storeName;
    private int rating;
    private String imgUrl;
    private String userId;
    private String comment;
    private String createDate;
    private String updateDate;
    private String storedFilename;

    private boolean replied;
    private String replyContent;
    private String replyDate;

}