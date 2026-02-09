package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.RestaurantDTO;
import bitc.next502.team_prj.dto.ReviewDTO;
import bitc.next502.team_prj.dto.RatingStatDTO;

import java.util.List;

public interface RestaurantService {
    List<RestaurantDTO> getTop3();

    RestaurantDTO getRestaurantDetail(String restaurantId);

    List<ReviewDTO> getReviewsByRestaurantId(String restaurantId);

    int getReviewCountByRestaurantId(String restaurantId);

    List<RatingStatDTO> getRatingStatsByRestaurantId(String restaurantId);


    List<RestaurantDTO> searchByFilter(String keyword, List<String> locations, List<String> categories, String sort);
}