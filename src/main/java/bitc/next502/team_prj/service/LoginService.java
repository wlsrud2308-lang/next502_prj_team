package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.BusinessUserDTO;
import bitc.next502.team_prj.dto.NormalUserDTO;

public interface LoginService {

  Object login(String id, String pw);

  NormalUserDTO findNormalUserByNameAndPhone(String name, String phone);

  //  이름, 전화번호로 사업자 아이디 조회
  BusinessUserDTO findBusinessUserByNameAndPhone(String name, String phone);

  // 아이디+이름+전화번호로 일반 비밀번호 조회
  NormalUserDTO findNormalUserByIdNamePhone(String id, String name, String phone);

  // 아이디+이름+전화번호로 사업자 비밀번호 조회
  BusinessUserDTO findBusinessUserByIdNamePhone(String id, String name, String phone);

  // 비밀번호 변경
  boolean updatePassword(String userType, String userId, String newPassword);

    boolean isIdDuplicate(String id);

  boolean isNormalNameDuplicate(String name);

  boolean isBusinessNameDuplicate(String businessName);
}
