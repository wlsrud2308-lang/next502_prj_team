package bitc.next502.team_prj.controller;

import bitc.next502.team_prj.dto.ReviewDTO;
import bitc.next502.team_prj.service.ReviewService;
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

    //레스토랑에 관련된 댓글리스트 @RequestParam("id") String id, Model model
    @GetMapping("/review/reviewList")
    public ModelAndView reviewList(@RequestParam("restaurantId") String restaurantId) throws Exception{

        if(restaurantId.equals("") || restaurantId == null){
           restaurantId = "Buk7096";  //gang3669   gang8428
        }

        List<ReviewDTO> reviewList = reviewService.selectReviewsList(restaurantId);
        ModelAndView mv = new ModelAndView("review/reviewList");
        mv.addObject("reviewList", reviewList);

        return mv;
    }


    //레스토랑에 관련된 댓글 상세보기
    @GetMapping("/review/reviewDetail/{reviewIdx}")
    public ModelAndView reviewDetail(@PathVariable("reviewIdx") int reviewId) throws Exception{

        ReviewDTO review=reviewService.selectReviewsDetail(reviewId);
        ModelAndView mv = new ModelAndView("review/reviewDetail");
        mv.addObject("review", review);
        return mv;

    }
    //Mypage에서 조회하는 내가 쓴 댓글들
    @RequestMapping("/review/myreviewList")
    public ModelAndView myReviewList(@RequestParam("userId") String userId) throws Exception{

        List<ReviewDTO> reviewList = reviewService.selectMyReviewsList(userId);
        ModelAndView mv = new ModelAndView("review/myreviewList");
        mv.addObject("reviewList", reviewList);
        return mv;

    }

    //댓글 등록 view
    @GetMapping("/review/reviewWrite")
    public String writeReview() {
        return "review/reviewWrite";
    }

   //댓글 등록처리
    @PostMapping("/review/reviewWrite")
    public String writeReview(ReviewDTO review) throws Exception{

        reviewService.insertReview(review);

        return "redirect:/mypage/main";
    }
    //댓글 등록처리 파일 포함
    @PostMapping("/review/reviewWriteFile")
    public String writeReviewFile(ReviewDTO review,
                              MultipartHttpServletRequest multipart,
                              HttpServletRequest req) throws Exception{

        reviewService.insertReviewFile(review,multipart);

        return "redirect:/mypage/main";
    }
    //예약 방운후 댓글 등록처리 파일 포함
    @PostMapping("/review/reviewWriteReservation")
    public String reviewWriteReservation(ReviewDTO review,
                                  MultipartHttpServletRequest multipart,
                                  HttpServletRequest req) throws Exception{

        reviewService.insertReviewFile(review,multipart);

        return "redirect:/mypage/main";
    }

    //댓글 수정
    @PostMapping("/review/reviewUpdate")
    public String reviewUpdate(ReviewDTO review,
                               MultipartHttpServletRequest multipart,
                               HttpServletRequest req) throws Exception{


        reviewService.updateReview(review,multipart);

        return "redirect:/mypage/main";
    }
    //댓글 삭제
//    @DeleteMapping("/review/{reviewIdx}")
//    public String deleteReview(@PathVariable("reviewIdx") int reviewIdx) throws Exception{
//        reviewService.deleteReview(reviewIdx);
//        return "redirect:/review/myreviewList";
//    }
//


    @RequestMapping("/review/delReview")
    public String delReview(@RequestParam("reviewIdx") int reviewIdx) throws Exception{

        reviewService.deleteReview(reviewIdx);
        return "redirect:/mypage/main";

    }




}
