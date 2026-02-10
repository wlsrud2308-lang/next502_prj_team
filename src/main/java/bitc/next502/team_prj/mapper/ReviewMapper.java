package bitc.next502.team_prj.mapper;

import bitc.next502.team_prj.dto.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {
    //식당별 리뷰리스트
    List<ReviewDTO> selectReviewsList(@Param("restaurantId") String restaurantId) throws Exception;
    //나의 리뷰리스트
    List<ReviewDTO> selectMyReviewsList(@Param("userId") String userId) throws Exception;
   // 리뷰상세
    ReviewDTO selectReviewsDetail(@Param("reviewIdx") int reviewId) throws Exception;
    //리뷰 등록
    void insertReview(ReviewDTO review) throws Exception;

    //방문완료 후 리뷰 등록
    void insertReviewByResvId(ReviewDTO review) throws Exception;


    //리뷰 수정
    void updateReview(ReviewDTO review) throws Exception;
    //리뷰 삭제
    void deleteReview(@Param("reviewIdx") int reviewId) throws Exception;


}


//@Param("restaurantId") String restaurantId