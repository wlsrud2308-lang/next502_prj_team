package bitc.next502.team_prj.dto;

import lombok.Data;

@Data
public class BookmarkDTO {
    private int idSeq;              // 순번
    private String userId;          // 유저 아이디
    private String createDate;      // 날짜
    private String restaurantId;
    private String restaurantName;
    private String mainImgThumb;
    private String address;
    private double rating;
    private String category;
    private String foods;
}