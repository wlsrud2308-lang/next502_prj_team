package bitc.next502.team_prj.controller;

import bitc.next502.team_prj.dto.BookmarkDTO; // 북마크 DTO 경로 확인
import bitc.next502.team_prj.dto.ReviewDTO;
import bitc.next502.team_prj.service.BookmarkService; // 북마크 서비스 경로 확인
import bitc.next502.team_prj.service.ReviewService;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private BookmarkService bookmarkService;

    // 레스토랑에 관련된 댓글리스트
    @GetMapping("/review/reviewList")
    public ModelAndView reviewList(@RequestParam("restaurantId") String restaurantId) throws Exception {
        if (restaurantId == null || restaurantId.equals("")) {
            restaurantId = "Buk7096";
        }

        List<ReviewDTO> reviewList = reviewService.selectReviewsList(restaurantId);
        ModelAndView mv = new ModelAndView("review/reviewList");
        mv.addObject("reviewList", reviewList);

        return mv;
    }

    // 레스토랑에 관련된 댓글 상세보기
    @GetMapping("/review/reviewDetail/{reviewIdx}")
    public ModelAndView reviewDetail(@PathVariable("reviewIdx") int reviewId) throws Exception {
        ReviewDTO review = reviewService.selectReviewsDetail(reviewId);
        ModelAndView mv = new ModelAndView("review/reviewDetail");
        mv.addObject("review", review);
        return mv;
    }

    // [수정] Mypage에서 조회하는 내가 쓴 댓글들 (사이드바 북마크 숫자 추가)
    @RequestMapping("/review/myreviewList")
    public ModelAndView myReviewList(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum,
                                     @RequestParam("userId") String userId) throws Exception {

        int navigatePages = 5; // 화면에 보여지는 페이지 버튼 수
        PageInfo<ReviewDTO> reviewList = new PageInfo<>(reviewService.selectMyReviewsList(pageNum, userId), navigatePages);

        ModelAndView mv = new ModelAndView("review/myreviewList");

        // 1. 리뷰 리스트 담기
        mv.addObject("reviewList", reviewList);

        // 2. [핵심] 사이드바 뱃지용 북마크 리스트 가져와서 담기
        List<BookmarkDTO> bookmarkList = bookmarkService.getBookmarkList(userId);
        mv.addObject("bookmarkList", bookmarkList);

        // 3. 사이드바 내부 링크 유지를 위해 userId도 함께 전달
        mv.addObject("userId", userId);

        return mv;
    }

    // 댓글 등록 view
    @GetMapping("/review/reviewWrite")
    public String writeReview() {
        return "review/reviewWrite";
    }

    // 댓글 등록처리
    @PostMapping("/review/reviewWrite")
    public String writeReview(ReviewDTO review) throws Exception {
        reviewService.insertReview(review);
        return "redirect:/mypage/main";
    }

    // 댓글 등록처리 파일 포함
    @PostMapping("/review/reviewWriteFile")
    public String writeReviewFile(ReviewDTO review,
                                  MultipartHttpServletRequest multipart,
                                  HttpServletRequest req) throws Exception {
        reviewService.insertReviewFile(review, multipart);
        return "redirect:/mypage/main";
    }

    // 예약 방문 후 댓글 등록처리 파일 포함
    @PostMapping("/review/reviewWriteReservation")
    public String reviewWriteReservation(ReviewDTO review,
                                         MultipartHttpServletRequest multipart,
                                         HttpServletRequest req) throws Exception {
        reviewService.insertReviewFile(review, multipart);
        return "redirect:/mypage/main";
    }

    // 댓글 수정
    @PostMapping("/review/reviewUpdate")
    public String reviewUpdate(ReviewDTO review,
                               MultipartHttpServletRequest multipart,
                               HttpServletRequest req) throws Exception {
        reviewService.updateReview(review, multipart);
        return "redirect:/mypage/main";
    }

    // 댓글 삭제
    @RequestMapping("/review/delReview")
    public String delReview(@RequestParam("reviewIdx") int reviewIdx) throws Exception {
        reviewService.deleteReview(reviewIdx);
        return "redirect:/mypage/main";
    }
}