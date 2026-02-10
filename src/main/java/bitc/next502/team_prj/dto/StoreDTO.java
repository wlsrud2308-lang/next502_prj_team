package bitc.next502.team_prj.dto;

import lombok.Data;

@Data
public class StoreDTO {
    private int storeId;        // 가게 고유 번호
    private String storeName;   // 가게 이름
    private String storeImg;    // 가게 이미지 주소
    private String category;    // 카테고리
    private double rating;      // 별점
    private String address;     // 주소
}