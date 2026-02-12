package bitc.next502.team_prj.controller;

import bitc.next502.team_prj.dto.*;
import bitc.next502.team_prj.service.MngService;
import bitc.next502.team_prj.service.RestaurantService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

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

        // 날짜 없으면 오늘로 세팅
        if (resvDate == null) {
            resvDate = LocalDate.now();
        }

        // 무조건 날짜 기준 조회
        List<MngDTO> resvList = mngService.getResvListByDateExcludeCanceled(businessId, resvDate);

        MngDTO mng = mngService.getMngInfo(businessId);

        int totalCount = resvList.size();
        int visitCount = (int) resvList.stream().filter(r -> "방문완료".equals(r.getStatus())).count();
        int waitingCount = (int) resvList.stream()
            .filter(r -> "방문예정".equals(r.getStatus()))
            .count();

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
                                @RequestParam("mainImgFile") MultipartFile mainImgFile,
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
            // 1. 식당 등록
            restaurantService.registerRestaurant(restaurantDTO);

            // 2. restaurant_id를 비즈니스 유저에 업데이트
            if (restaurantDTO.getRestaurantId() == null) {
                throw new RuntimeException("식당 ID가 생성되지 않았습니다.");
            }

            BusinessUserDTO businessUser = (BusinessUserDTO) userBoxing;
            String businessId = businessUser.getBusinessId();
            long restaurantId = restaurantDTO.getRestaurantId();  // 새로 등록된 restaurant_id

            // restaurant_id를 business_user 테이블에 업데이트
            mngService.updateRestaurantIdForBusinessUser(businessId, restaurantId);

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

    // 관리자 리뷰 관리
    @GetMapping("/mngreview")
    public String mngreview(Model model,
                            HttpSession session,
                            @RequestParam(defaultValue = "0") int page,   // 현재 페이지
                            @RequestParam(defaultValue = "3") int size) { // 한 페이지 리뷰 수

        String role = (String) session.getAttribute("role");
        Object userBoxing = session.getAttribute("loginUser");

        if (role == null || userBoxing == null || !"BUSINESS".equals(role)) {
            return "redirect:/login";
        }

        BusinessUserDTO businessUser = (BusinessUserDTO) userBoxing;
        String businessId = businessUser.getBusinessId();

        // 페이징 리뷰 조회
        List<ReviewDTO> reviewList = mngService.getReviewListByBusinessId(businessId, page, size);

        // 총 리뷰 수 조회
        int totalReviews = mngService.countReviewByBusinessId(businessId);

        // 총 페이지 계산
        int totalPages = (int) Math.ceil((double) totalReviews / size);

        // 미답변 개수 (현재 페이지 기준)
        int unansweredCount = (int) reviewList.stream()
            .filter(r -> r.getReplyContent() == null || r.getReplyContent().isEmpty())
            .count();

        model.addAttribute("unansweredCount", unansweredCount);
        model.addAttribute("menuId", "review");
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", size); // optional, 페이지 UI에서 필요할 수도 있음

        return "mng/mngreview";
    }

    // 관리자 리뷰 답글
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

    // 방문확인
    @PatchMapping("/mngmenu/confirm/{resvId}")
    @ResponseBody
    public ResponseEntity<?> confirmVisit(@PathVariable int resvId, HttpSession session) {
        String role = (String) session.getAttribute("role");
        Object userBoxing = session.getAttribute("loginUser");
        if(role == null || userBoxing == null || !"BUSINESS".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("success", false));
        }

        mngService.updateReservationState(resvId, "방문완료");

        BusinessUserDTO businessUser = (BusinessUserDTO) userBoxing;
        String businessId = businessUser.getBusinessId();
        List<MngDTO> resvList = mngService.getResvListByDate(businessId, LocalDate.now());

        int totalCount = resvList.size();
        int visitCount = (int) resvList.stream().filter(r -> "방문완료".equals(r.getStatus())).count();
        int waitingCount = (int) resvList.stream()
            .filter(r -> "방문예정".equals(r.getStatus()))
            .count();

        return ResponseEntity.ok(Map.of(
            "success", true,
            "totalCount", totalCount,
            "visitCount", visitCount,
            "waitingCount", waitingCount
        ));
    }

    // 노쇼 처리
    @PatchMapping("/mngmenu/noshow/{resvId}")
    @ResponseBody
    public ResponseEntity<?> handleNoShow(@PathVariable int resvId, HttpSession session) {
        String role = (String) session.getAttribute("role");
        Object userBoxing = session.getAttribute("loginUser");
        if(role == null || userBoxing == null || !"BUSINESS".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("success", false));
        }

        mngService.updateReservationState(resvId, "노쇼");

        BusinessUserDTO businessUser = (BusinessUserDTO) userBoxing;
        String businessId = businessUser.getBusinessId();
        List<MngDTO> resvList = mngService.getResvListByDate(businessId, LocalDate.now());

        int totalCount = resvList.size();
        int visitCount = (int) resvList.stream().filter(r -> "방문완료".equals(r.getStatus())).count();
        int waitingCount = (int) resvList.stream()
            .filter(r -> "방문예정".equals(r.getStatus()))
            .count();

        return ResponseEntity.ok(Map.of(
            "success", true,
            "totalCount", totalCount,
            "visitCount", visitCount,
            "waitingCount", waitingCount
        ));
    }

    // 예약 취소
    @DeleteMapping("/mngmenu/cancel/{resvId}")
    @ResponseBody
    public ResponseEntity<?> cancelReservation(@PathVariable int resvId, HttpSession session) {
        String role = (String) session.getAttribute("role");
        Object userBoxing = session.getAttribute("loginUser");
        if(role == null || userBoxing == null || !"BUSINESS".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("success", false));
        }

        try {
            mngService.cancelReservation(resvId);

            BusinessUserDTO businessUser = (BusinessUserDTO) userBoxing;
            String businessId = businessUser.getBusinessId();
            List<MngDTO> resvList = mngService.getResvListByDate(businessId, LocalDate.now());

            int totalCount = resvList.size();
            int visitCount = (int) resvList.stream().filter(r -> "방문완료".equals(r.getStatus())).count();
            int waitingCount = (int) resvList.stream()
                .filter(r -> "방문예정".equals(r.getStatus()))
                .count();

            return ResponseEntity.ok(Map.of(
                "success", true,
                "totalCount", totalCount,
                "visitCount", visitCount,
                "waitingCount", waitingCount
            ));
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false));
        }
    }

    @GetMapping("/mngmypage")
    public ModelAndView mngMyPage(HttpSession session) {
        ModelAndView mv = new ModelAndView("mng/mngmypage");
        BusinessUserDTO loginUser = (BusinessUserDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            mv.setViewName("redirect:/login");
            return mv;
        }

        MngDTO userInfo = mngService.getMngInfo(loginUser.getBusinessId());
        mv.addObject("userInfo", userInfo);
        mv.addObject("menuId", "profile");

        return mv;
    }

    @PostMapping("/mng/updateProfile")
    public String updateProfile(@ModelAttribute BusinessUserDTO userDTO, RedirectAttributes ra, HttpSession session) {
        // 1. 실제 DB 업데이트 수행
        // MngService에 이 메서드를 추가해야 합니다 (아래 2, 3단계 참고)
        mngService.updateBusinessUserInfo(userDTO);

        // 2. 세션 정보 갱신 (로그인 세션이 옛날 정보를 들고 있으면 화면이 안 바뀜)
        BusinessUserDTO loginUser = (BusinessUserDTO) session.getAttribute("loginUser");
        if (loginUser != null) {
            loginUser.setBusinessName(userDTO.getBusinessName());
            loginUser.setBusinessPhone(userDTO.getBusinessPhone());
            // 비밀번호를 입력했을 때만 세션/DB에 반영
            if (userDTO.getBusinessPwd() != null && !userDTO.getBusinessPwd().isEmpty()) {
                loginUser.setBusinessPwd(userDTO.getBusinessPwd());
            }
            session.setAttribute("loginUser", loginUser);
        }

        ra.addFlashAttribute("alertMessage", "정보가 성공적으로 수정되었습니다.");
        return "redirect:/mngmypage";
    }

    @PostMapping("/mngmypage/deleteAccount")
    public String deleteAccountFromMyPage(@RequestParam String password,
                                          HttpSession session,
                                          RedirectAttributes redirectAttributes) {
        String role = (String) session.getAttribute("role");
        Object userBoxing = session.getAttribute("loginUser");

        if (role == null || userBoxing == null || !"BUSINESS".equals(role)) {
            return "redirect:/login";
        }

        BusinessUserDTO businessUser = (BusinessUserDTO) userBoxing;

        boolean deleted = mngService.scheduleBusinessAccountDeletion(businessUser.getBusinessId(), password);

        if (deleted) {
            session.invalidate(); // 세션 종료
            redirectAttributes.addFlashAttribute("alertMessage", "계정 삭제가 예약되었습니다. 7일 후 자동 삭제됩니다.");
            redirectAttributes.addFlashAttribute("alertType", "success");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("alertMessage", "비밀번호가 올바르지 않습니다.");
            redirectAttributes.addFlashAttribute("alertType", "danger");
            return "redirect:/mngmypage"; // 실패 시 마이페이지로
        }
    }

    // GET: 식당 정보 수정 페이지
    @GetMapping("/mngStoreEdit")
    public String editRestaurant(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String role = (String) session.getAttribute("role");
        Object userBoxing = session.getAttribute("loginUser");

        if (role == null || userBoxing == null || !"BUSINESS".equals(role)) {
            return "redirect:/login";
        }

        BusinessUserDTO businessUser = (BusinessUserDTO) userBoxing;

        // businessUser에 연결된 식당 정보 가져오기
        RestaurantDTO restaurant = restaurantService.getRestaurantById(businessUser.getRestaurantId());

        if (restaurant == null) {
            redirectAttributes.addFlashAttribute("alertMessage", "등록된 식당 정보가 없습니다. 먼저 식당을 등록해주세요.");
            redirectAttributes.addFlashAttribute("alertType", "warning");
            return "redirect:/mngstoreWrite";
        }

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("menuId", "store");
        // templates/mng/mngStoreEdit.html 렌더링
        return "mng/mngStoreEdit";
    }

        // POST: 식당 정보 수정 처리
        @PostMapping("/mngStoreUpdate")
        public String updateRestaurant(@ModelAttribute RestaurantDTO restaurantDTO,
                @RequestParam("mainImgFile") MultipartFile mainImgFile,
                HttpSession session,
                RedirectAttributes redirectAttributes) throws IOException {

            System.out.println("수정 요청: " + restaurantDTO);

            String role = (String) session.getAttribute("role");
            Object userBoxing = session.getAttribute("loginUser");
            if (role == null || userBoxing == null || !"BUSINESS".equals(role)) {
                return "redirect:/login";
            }

            if (!mainImgFile.isEmpty()) {
                String fileName = saveFile(mainImgFile);
                restaurantDTO.setMainImg(fileName);
            }

            restaurantService.updateRestaurantInfo(restaurantDTO);
            redirectAttributes.addFlashAttribute("alertMessage", "식당 정보가 수정되었습니다.");
            redirectAttributes.addFlashAttribute("alertType", "success");
            return "redirect:/mngStoreEdit";
    }

}
