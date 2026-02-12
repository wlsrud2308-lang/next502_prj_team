package bitc.next502.team_prj.dto;

import lombok.Data;

@Data
public class RestaurantDTO {
    private String restaurantId;
    private String restaurantName;
    private String category;
    private String foods;
    private String address;
    private double rating;
    private String restaurantTel;
    private String mainImg;
    private String itemCntnts;

    private String locationTag;
    private String seats;
    private String description;
    private String lat;
    private String lng;
    private String mainImgThumb;
    private String price;
    private String maxCapacity;
}