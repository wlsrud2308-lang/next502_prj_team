package bitc.next502.team_prj.controller;

import bitc.next502.team_prj.dto.MyInfoDTO;
import bitc.next502.team_prj.dto.ReviewDTO;
import bitc.next502.team_prj.service.MyPageServiceImpl;
import bitc.next502.team_prj.service.ReviewService;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
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
    private MyPageServiceImpl myPageService;

    @Autowired
    private bitc.next502.team_prj.service.BookmarkService bookmarkService;

    // 1. 식당 리뷰 리스트
    @GetMapping("/review/reviewList")
    public ModelAndView reviewList(@RequestParam("restaurantId") String restaurantId) throws Exception{
        if(restaurantId.equals("") || restaurantId == null){
            restaurantId = "Buk7096";
        }
        List<ReviewDTO> reviewList = reviewService.selectReviewsList(restaurantId);
        ModelAndView mv = new ModelAndView("review/reviewList");
        mv.addObject("reviewList", reviewList);
        return mv;
    }

    // 2. 리뷰 상세
    @GetMapping("/review/reviewDetail/{reviewIdx}")
    public ModelAndView reviewDetail(@PathVariable("reviewIdx") int reviewId) throws Exception{
        ReviewDTO review = reviewService.selectReviewsDetail(reviewId);
        ModelAndView mv = new ModelAndView("review/reviewDetail");
        mv.addObject("review", review);
        return mv;
    }
<<<<<<< HEAD

    // 3. 내가 쓴 리뷰 (유저 정보 추가)
    @RequestMapping("/review/myreviewList")
    public ModelAndView myReviewList(@RequestParam("userId") String userId) throws Exception{
=======
    //Mypage에서 조회하는 내가 쓴 댓글들 페이지처리
    @RequestMapping("/review/myreviewList")
    public ModelAndView myReviewList(@RequestParam(required = false,defaultValue="1",value="pageNum") int pageNum,
                                     @RequestParam("userId") String userId) throws Exception{
        int navigatePages = 5; //화면에 보여지는 페이지버튼수
        PageInfo<ReviewDTO> reviewList = new PageInfo<>( reviewService.selectMyReviewsList(pageNum,userId),navigatePages);
>>>>>>> be747b98cbe6ee7710df417ed557b627681c822b
        ModelAndView mv = new ModelAndView("review/myreviewList");

        // (1) 리뷰 목록 가져오기
        List<ReviewDTO> reviewList = reviewService.selectMyReviewsList(userId);
        mv.addObject("reviewList", reviewList);

        // (2) 로그인한 유저 정보 가져오기
        MyInfoDTO userInfo = myPageService.getMyInfo(userId);

        // 안전장치
        if (userInfo == null) {
            userInfo = new MyInfoDTO();
            userInfo.setName("회원");
            userInfo.setGrade("일반");
        }

        // (3) 화면으로 정보 보내기
        List<bitc.next502.team_prj.dto.BookmarkDTO> bookmarkList = bookmarkService.getBookmarkList(userId);
        mv.addObject("bookmarkList", bookmarkList);
        mv.addObject("userInfo", userInfo);

        return mv;
    }

    @GetMapping("/review/reviewWrite")
    public String writeReview() {
        return "review/reviewWrite";
    }

    @PostMapping("/review/reviewWrite")
    public String writeReview(ReviewDTO review) throws Exception{
        reviewService.insertReview(review);
        return "redirect:/mypage/main";
    }

    @PostMapping("/review/reviewWriteFile")
    public String writeReviewFile(ReviewDTO review, MultipartHttpServletRequest multipart, HttpServletRequest req) throws Exception{
        reviewService.insertReviewFile(review, multipart);
        return "redirect:/mypage/main";
    }

    @PostMapping("/review/reviewWriteReservation")
    public String reviewWriteReservation(ReviewDTO review, MultipartHttpServletRequest multipart, HttpServletRequest req) throws Exception{
        reviewService.insertReviewFile(review, multipart);
        return "redirect:/mypage/main";
    }

    @PostMapping("/review/reviewUpdate")
<<<<<<< HEAD
    public String reviewUpdate(ReviewDTO review, MultipartHttpServletRequest multipart, HttpServletRequest req) throws Exception{
        reviewService.updateReview(review, multipart);
        return "redirect:/mypage/main";
    }

=======
    public String reviewUpdate(ReviewDTO review,
                               MultipartHttpServletRequest multipart,
                               HttpServletRequest req) throws Exception{

        reviewService.updateReview(review,multipart);

        return "redirect:/mypage/main";
    }

    //댓글 삭제
>>>>>>> be747b98cbe6ee7710df417ed557b627681c822b
    @RequestMapping("/review/delReview")
    public String delReview(@RequestParam("reviewIdx") int reviewIdx) throws Exception{
        reviewService.deleteReview(reviewIdx);
        return "redirect:/mypage/main";
    }
}