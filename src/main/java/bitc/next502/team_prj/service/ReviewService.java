package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {

//식당별 리뷰리스트
    List<ReviewDTO> selectReviewsList(String restaurantId) throws Exception;
    List<ReviewDTO> selectMyReviewsList(String restaurantId) throws Exception;
// 리뷰상세
    ReviewDTO selectReviewsDetail(int reviewId) throws Exception;
//리뷰 등록
    void insertReview(ReviewDTO review) throws Exception;
//리뷰 수정
    void updateReview(ReviewDTO review) throws Exception;
//리뷰 삭제
    void deleteReview(int reviewId) throws Exception;



}
