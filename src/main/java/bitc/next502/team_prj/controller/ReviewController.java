package bitc.next502.team_prj.controller;

import bitc.next502.team_prj.dto.BookmarkDTO;
import bitc.next502.team_prj.dto.ReviewDTO;
import bitc.next502.team_prj.service.BookmarkService;
import bitc.next502.team_prj.service.ReviewService;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // 알림 메시지용 추가

import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private BookmarkService bookmarkService;

    // 레스토랑에 관련된 댓글리스트
    @GetMapping("/review/reviewList")
    public ModelAndView reviewList(@RequestParam(value = "restaurantId", required = false, defaultValue = "Buk7096") String restaurantId) throws Exception {
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

    // Mypage에서 조회하는 내가 쓴 댓글들
    @RequestMapping("/review/myreviewList")
    public ModelAndView myReviewList(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum,
                                     @RequestParam("userId") String userId) throws Exception {

        int navigatePages = 5;
        PageInfo<ReviewDTO> reviewList = new PageInfo<>(reviewService.selectMyReviewsList(pageNum, userId), navigatePages);

        ModelAndView mv = new ModelAndView("review/myreviewList");
        mv.addObject("reviewList", reviewList);

        // 사이드바 뱃지용 북마크 리스트
        List<BookmarkDTO> bookmarkList = bookmarkService.getBookmarkList(userId);
        mv.addObject("bookmarkList", bookmarkList);
        mv.addObject("userId", userId);

        return mv;
    }

    // 댓글 등록처리 (예약 방문 후)
    @PostMapping("/review/reviewWriteReservation")
    public String reviewWriteReservation(ReviewDTO review,
                                         MultipartHttpServletRequest multipart,
                                         RedirectAttributes reAttr) throws Exception {
        reviewService.insertReviewFile(review, multipart);
        reAttr.addFlashAttribute("msg", "리뷰가 성공적으로 등록되었습니다!");
        return "redirect:/mypage/main"; // 등록 후에는 예약 내역으로 이동
    }

    @PostMapping("/review/reviewUpdate")
    public String reviewUpdate(ReviewDTO review,
                               @RequestParam("userId") String userId, // 👈 삭제 로직처럼 userId를 직접 받음
                               MultipartHttpServletRequest multipart,
                               RedirectAttributes reAttr) throws Exception {

        reviewService.updateReview(review, multipart);

        reAttr.addFlashAttribute("msg", "리뷰가 수정되었습니다.");
        return "redirect:/review/myreviewList?userId=" + userId;
    }

    // 댓글 삭제 처리
    @RequestMapping("/review/delReview")
    public String delReview(@RequestParam("reviewIdx") int reviewIdx,
                            @RequestParam("userId") String userId,
                            RedirectAttributes reAttr) throws Exception {
        reviewService.deleteReview(reviewIdx);

        // 삭제 완료 후 보던 리뷰 목록으로 돌아가기
        reAttr.addFlashAttribute("msg", "리뷰가 삭제되었습니다.");
        return "redirect:/review/myreviewList?userId=" + userId;
    }

    // 기타 단순 등록 처리들
    @PostMapping("/review/reviewWrite")
    public String writeReview(ReviewDTO review, RedirectAttributes reAttr) throws Exception {
        reviewService.insertReview(review);
        reAttr.addFlashAttribute("msg", "리뷰가 등록되었습니다.");
        return "redirect:/mypage/main";
    }

    @PostMapping("/review/reviewWriteFile")
    public String writeReviewFile(ReviewDTO review,
                                  MultipartHttpServletRequest multipart,
                                  RedirectAttributes reAttr) throws Exception {
        reviewService.insertReviewFile(review, multipart);
        reAttr.addFlashAttribute("msg", "리뷰가 등록되었습니다.");
        return "redirect:/mypage/main";
    }
}