package bitc.next502.team_prj.dto;

import lombok.Data;

@Data
public class RestaurantDTO {
    private long restaurantId;
    private String restaurantName;
    private String category;
    private String foods;
    private String address;
    private double rating;
    private String restaurantTel;
    private String mainImg;
    private String itemCntnts;
    private String usageDayWeekAndTime;
    private String menu;


    private String locationTag;
    private String seats;
    private String description;
    private String lat;
    private String lng;
    private String mainImgThumb;
    private String price;
    private String maxCapacity;

    // Getters and Setters
    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}