package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.BusinessUserDTO;
import bitc.next502.team_prj.dto.MngDTO;
import bitc.next502.team_prj.dto.ReviewDTO;

import java.time.LocalDate;
import java.util.List;

public interface MngService {

  MngDTO getMngInfo(String businessId);

  List<MngDTO> getResvList(String businessId);

  List<MngDTO> getResvListByDate(String businessId, LocalDate resvDate);

  // 리뷰 + 사장 댓글 조회
  List<ReviewDTO> getReviewListByBusinessId(String businessId, int page, int size);

  // 총 리뷰 수 조회
  int countReviewByBusinessId(String businessId);

  // 사장 댓글 등록/수정
  void updateReviewReply(int reviewIdx, String replyContent);

  void updateReservationState(int resvId, String newState);

  void cancelReservation(int resvId);

  List<MngDTO> getResvListByDateExcludeCanceled(String businessId, LocalDate resvDate);

  void updateBusinessUserInfo(BusinessUserDTO userDTO);


}
