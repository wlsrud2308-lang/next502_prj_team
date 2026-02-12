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
    public void registerRestaurant(RestaurantDTO restaurantDTO) {
        // restaurant_id 자동 생성 후 DTO에 반영
        restaurantMapper.insertRestaurant(restaurantDTO);  // insert 이후 restaurantDTO.getRestaurantId()가 자동으로 채워짐
        if (restaurantDTO.getRestaurantId() == null) {
            throw new RuntimeException("식당 ID가 생성되지 않았습니다.");
        }
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
        return restaurantMapper.getTotalCount();
    }
}
