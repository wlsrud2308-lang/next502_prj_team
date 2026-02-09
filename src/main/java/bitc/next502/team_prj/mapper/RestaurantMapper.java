package bitc.next502.team_prj.mapper; // 이 경로가 정확해야 합니다.

import bitc.next502.team_prj.dto.RestaurantDTO;
import bitc.next502.team_prj.dto.ReviewDTO;
import bitc.next502.team_prj.dto.RatingStatDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RestaurantMapper {
    List<RestaurantDTO> selectTop3Restaurants();

    RestaurantDTO selectRestaurantDetail(@Param("restaurantId") String restaurantId);

    List<ReviewDTO> selectReviewsByRestaurantId(@Param("restaurantId") String restaurantId);

    int selectReviewCountByRestaurantId(@Param("restaurantId") String restaurantId);

    List<RatingStatDTO> selectRatingStatsByRestaurantId(@Param("restaurantId") String restaurantId);

    List<RestaurantDTO> searchByKeyword(@Param("keyword") String keyword);

    List<RestaurantDTO> searchByFilter(
            @Param("keyword") String keyword,
            @Param("locations") List<String> locations,
            @Param("categories") List<String> categories,
            @Param("sort") String sort
    );

}