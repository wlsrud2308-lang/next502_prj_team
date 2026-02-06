package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.RestaurantDTO;
import bitc.next502.team_prj.dto.ReviewDTO;
import bitc.next502.team_prj.dto.RatingStatDTO;
import bitc.next502.team_prj.mapper.RestaurantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Override
    public List<RestaurantDTO> getTop3() {
        return restaurantMapper.selectTop3Restaurants();
    }

    @Override
    public RestaurantDTO getRestaurantDetail(String restaurantId) {
        return restaurantMapper.selectRestaurantDetail(restaurantId);
    }

    @Override
    public List<ReviewDTO> getReviewsByRestaurantId(String restaurantId) {
        return restaurantMapper.selectReviewsByRestaurantId(restaurantId);
    }

    @Override
    public int getReviewCountByRestaurantId(String restaurantId) {
        return restaurantMapper.selectReviewCountByRestaurantId(restaurantId);
    }

    @Override
    public List<RatingStatDTO> getRatingStatsByRestaurantId(String restaurantId) {
        return restaurantMapper.selectRatingStatsByRestaurantId(restaurantId);
    }

    @Override
    public List<RestaurantDTO> searchByKeyword(String keyword) {
        return restaurantMapper.searchByKeyword(keyword);
    }

}