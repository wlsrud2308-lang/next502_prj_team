package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.ReviewDTO;
import bitc.next502.team_prj.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;


    @Override
    public List<ReviewDTO> selectReviewsList(String restaurantId) throws Exception {
        //댓글 조회
        return reviewMapper.selectReviewsList(restaurantId);
    }

    @Override
    public List<ReviewDTO> selectMyReviewsList(String userId) throws Exception {
        //나의 댓글 조회
        return reviewMapper.selectMyReviewsList(userId);
    }

    @Override
    public ReviewDTO selectReviewsDetail(int reviewId) throws Exception {
        //댓글 상세조회
        return reviewMapper.selectReviewsDetail(reviewId);
    }

    @Override
    public void insertReview(ReviewDTO review) throws Exception {
        reviewMapper.insertReview(review);

    }

    @Override
    public void updateReview(ReviewDTO review) throws Exception {
        reviewMapper.updateReview(review);
    }

    @Override
    public void deleteReview(int reviewId) throws Exception {
        reviewMapper.deleteReview(reviewId);
    }
}
