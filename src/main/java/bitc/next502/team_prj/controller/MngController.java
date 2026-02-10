package bitc.next502.team_prj.controller;

import bitc.next502.team_prj.dto.*;
import bitc.next502.team_prj.service.MngService;
import bitc.next502.team_prj.service.RestaurantService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class MngController {

    @Autowired
    private MngService mngService;

    @Autowired
    private RestaurantService restaurantService;

    // 예약자 명단 관리
    @GetMapping("/mngmenu")
    public String mngMenu(Model model,
                          HttpSession session,
                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate resvDate) {

        String role = (String) session.getAttribute("role");
        Object userBoxing = session.getAttribute("loginUser");
        model.addAttribute("menuId", "menu");

        if (role == null || userBoxing == null) {
            return "redirect:/login";
        }

        if (!"BUSINESS".equals(role)) {
            return "redirect:/login";
        }

        BusinessUserDTO businessUser = (BusinessUserDTO) userBoxing;
        String businessId = businessUser.getBusinessId();

        // ⭐ 핵심: 날짜 없으면 오늘로 세팅
        if (resvDate == null) {
            resvDate = LocalDate.now();
        }

        // ⭐ 무조건 날짜 기준 조회
        List<MngDTO> resvList =
            mngService.getResvListByDate(businessId, resvDate);

        MngDTO mng = mngService.getMngInfo(businessId);

        int totalCount = resvList.size();
        int visitCount = (int) resvList.stream().filter(r -> "방문완료".equals(r.getStatus())).count();
        int waitingCount = totalCount - visitCount;

        model.addAttribute("mng", mng);
        model.addAttribute("resvList", resvList);
        model.addAttribute("resvDate", resvDate);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("visitCount", visitCount);
        model.addAttribute("waitingCount", waitingCount);

        return "mng/mngmenu";
    }

    @GetMapping("/mngstoreWrite")
    public String mngstoreWrite() {
        return "mng/mngstoreWrite";
    }

    // POST: 가게 등록 처리
    @PostMapping("/mngstoreWrite")
    public String registerStore(@ModelAttribute RestaurantDTO restaurantDTO,
                                @RequestParam("mainImg") MultipartFile mainImgFile,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) throws IOException {

        String role = (String) session.getAttribute("role");
        Object userBoxing = session.getAttribute("loginUser");

        if (role == null || userBoxing == null || !"BUSINESS".equals(role)) {
            return "redirect:/login";
        }

        // 이미지 파일 처리
        if (!mainImgFile.isEmpty()) {
            String fileName = saveFile(mainImgFile);
            restaurantDTO.setMainImg(fileName);
        }

        try {
            restaurantService.registerRestaurant(restaurantDTO);
            // 성공 메시지
            redirectAttributes.addFlashAttribute("alertMessage", "가게 등록이 완료되었습니다!");
            redirectAttributes.addFlashAttribute("alertType", "success");
        } catch (Exception e) {
            e.printStackTrace();
            // 실패 메시지
            redirectAttributes.addFlashAttribute("alertMessage", "가게 등록 중 오류가 발생했습니다.");
            redirectAttributes.addFlashAttribute("alertType", "danger");
        }

        return "redirect:/mngmenu";  // 리다이렉트
    }

    // 파일 저장 메서드
    private String saveFile(MultipartFile file) throws IOException {
        String uploadDir = "uploads/";
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
        return "/uploads/" + fileName;
    }

    @GetMapping("/mngreview")
    public String mngreview(Model model, HttpSession session) {

        String role = (String) session.getAttribute("role");
        Object userBoxing = session.getAttribute("loginUser");

        if (role == null || userBoxing == null || !"BUSINESS".equals(role)) {
            return "redirect:/login";
        }

        BusinessUserDTO businessUser = (BusinessUserDTO) userBoxing;
        String businessId = businessUser.getBusinessId();

        // ⭐ 리뷰 + 사장 댓글 같이 조회
        List<ReviewDTO> reviewList = mngService.getReviewListByBusinessId(businessId);
        int unansweredCount = (int) reviewList.stream()
                .filter(r -> r.getReplyContent() == null || r.getReplyContent().isEmpty())
                .count();

        model.addAttribute("unansweredCount", unansweredCount);
        model.addAttribute("menuId", "review");
        model.addAttribute("reviewList", reviewList);

        return "mng/mngreview";
    }

    @PostMapping("/mngreview/reply")
    public String postReviewReply(@RequestParam int reviewIdx,
                                  @RequestParam String replyContent,
                                  HttpSession session) {
        String role = (String) session.getAttribute("role");
        Object userBoxing = session.getAttribute("loginUser");

        if (role == null || userBoxing == null || !"BUSINESS".equals(role)) {
            return "redirect:/login";
        }

        mngService.updateReviewReply(reviewIdx, replyContent);

        return "redirect:/mngreview";
    }

//    // 식당 정보 등록
//    @GetMapping("/mngstoreWrite")
//    public String mngStoreWrite(Model model) {
//        model.addAttribute("menuId", "store"); // 'store'라는 별명을 붙임
//        return "mng/mngstoreWrite";
//    }
//
//    // 리뷰 답변 관리
//    @GetMapping("/mngreview")
//    public String mngReview(Model model) {
//        model.addAttribute("menuId", "review"); // 'review'라는 별명을 붙임
//        return "mng/mngreview";
//    }
}
