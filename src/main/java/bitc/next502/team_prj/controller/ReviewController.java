package bitc.next502.team_prj.controller;

import bitc.next502.team_prj.dto.ReviewDTO;
import bitc.next502.team_prj.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

//  RESTful 방식의 전체 URL 설정사용

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
    @GetMapping("/review/myreviewList")
    public ModelAndView myReviewList(@RequestParam("userId") String userId) throws Exception{

        List<ReviewDTO> reviewList = reviewService.selectMyReviewsList(userId);
        ModelAndView mv = new ModelAndView("review/reviewList");
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

        return "redirect:/main";
    }

    //댓글 수정
    @PutMapping("/review/{reviewIdx}")
    public String reviwqWrite(ReviewDTO review) throws Exception{
        reviewService.updateReview(review);
        return "redirect:/review/reviewList";
    }
    //댓글 삭제
    @DeleteMapping("/review/{reviewIdx}")
    public String deleteReview(@PathVariable("reviewIdx") int reviewIdx) throws Exception{
        reviewService.deleteReview(reviewIdx);
        return "redirect:/review/myreviewList";
    }




}
