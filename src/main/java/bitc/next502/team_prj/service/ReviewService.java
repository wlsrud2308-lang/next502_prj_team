package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.ReviewDTO;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface ReviewService {

//식당별 리뷰리스트
    List<ReviewDTO> selectReviewsList(String restaurantId) throws Exception;
//나의 댓글리스트
    Page<ReviewDTO> selectMyReviewsList(int pageNum, String userId) throws Exception;
// 리뷰상세
    ReviewDTO selectReviewsDetail(int reviewId) throws Exception;
//리뷰 등록
    void insertReview(ReviewDTO review) throws Exception;
//리뷰파일 포함 등록
    void insertReviewFile(ReviewDTO review, MultipartHttpServletRequest multipart) throws Exception;

    //리뷰 수정(파일포함)
    void updateReview(ReviewDTO review,MultipartHttpServletRequest multipart) throws Exception;
//리뷰 삭제
    void deleteReview(int reviewId) throws Exception;



}
