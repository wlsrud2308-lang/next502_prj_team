package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.RestaurantDTO;
import bitc.next502.team_prj.dto.ReviewDTO;
import bitc.next502.team_prj.dto.RatingStatDTO;
import bitc.next502.team_prj.mapper.RestaurantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public List<RestaurantDTO> searchByFilter(String keyword, List<String> locations, List<String> categories, String sort) {
        return restaurantMapper.searchByFilter(keyword, locations, categories, sort);
    }

    @Override
    public void registerRestaurant(RestaurantDTO dto) {
        restaurantMapper.insertRestaurant(dto);
    }

    @Override
    public void updateRestaurantInfo(RestaurantDTO dto) throws IOException {
        restaurantMapper.updateRestaurant(dto);
    }

    @Override
    public RestaurantDTO getRestaurantById(String restaurantId) {
        return restaurantMapper.selectRestaurantById(restaurantId);
    }

    @Override
    public int getTotalCount() {
        return restaurantMapper.getTotalCount(); // mapper 호출
    }
}