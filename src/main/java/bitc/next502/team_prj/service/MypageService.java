package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.MyInfoDTO;
import bitc.next502.team_prj.dto.MyResvDTO;
import com.github.pagehelper.Page;

import java.util.List;

public interface MypageService {

  // 내 정보 가져오기
  MyInfoDTO getMyInfo(String userId);

  // 예약 목록 가져오기
  //List<MyResvDTO> getMyResvList(String userId);
  // 예약 목록 가져오기 -페이징처리
  Page<MyResvDTO> getMyResvList(int pageNum, String userId);

  // 예약 취소
  void cancelReservation(int resvId);

//  유저 계정 삭제
  boolean deleteNormalUserAccount(String userId, String password);
}

