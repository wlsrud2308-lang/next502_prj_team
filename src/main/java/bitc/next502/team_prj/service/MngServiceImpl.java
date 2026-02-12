package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.BusinessUserDTO;
import bitc.next502.team_prj.dto.ReviewDTO;
import bitc.next502.team_prj.dto.MngDTO;
import bitc.next502.team_prj.mapper.MngMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MngServiceImpl implements MngService{

  @Autowired
  private MngMapper mngMapper;

  @Override
  public MngDTO getMngInfo(String businessId) {
    return mngMapper.selectMngById(businessId);
  }

  @Override
  public List<MngDTO> getResvList(String businessID) {
      return mngMapper.selectResvList(businessID);
  }

  @Override
  public List<MngDTO> getResvListByDate(String businessId, LocalDate resvDate) {
    return mngMapper.selectResvListByDate(businessId, resvDate);
  }

  @Override
  public List<ReviewDTO> getReviewListByBusinessId(String businessId, int page, int size) {
    int offset = page * size; // 페이징 오프셋 계산
    return mngMapper.selectReviewListByBusinessIdWithPaging(businessId, offset, size);
  }

  @Override
  public int countReviewByBusinessId(String businessId) {
    return mngMapper.countReviewByBusinessId(businessId);
  }

  @Override
  public void updateReviewReply(int reviewIdx, String replyContent) {
    mngMapper.updateReviewReply(reviewIdx, replyContent);
  }

  @Override
  public void updateReservationState(int resvId, String newState) {
    mngMapper.updateReservationState(resvId, newState);
  }

  @Override
  public void cancelReservation(int resvId) {
    mngMapper.updateReservationState(resvId, "취소");
  }

  @Override
  public List<MngDTO> getResvListByDateExcludeCanceled(String businessId, LocalDate resvDate) {
    return mngMapper.selectResvListByDateExcludeCanceled(businessId, resvDate);
  }

  @Override
  public void updateBusinessUserInfo(BusinessUserDTO userDTO) {
    mngMapper.updateBusinessUserInfo(userDTO);
  }

  // 사업자 정보 조회
  @Override
  public BusinessUserDTO getBusinessById(String businessId) {
    return mngMapper.selectBusinessById(businessId);
  }

  // 사업자 정보 수정 (비밀번호 평문)
  @Override
  public void updateBusinessInfo(String businessId, String businessName, String businessPhone, String newPassword) {
    BusinessUserDTO business = mngMapper.selectBusinessById(businessId);
    if (business == null) return;

    business.setBusinessName(businessName);
    business.setBusinessPhone(businessPhone);

    if (newPassword != null && !newPassword.isEmpty()) {
      business.setBusinessPwd(newPassword); // 평문 저장
    }

    mngMapper.updateBusiness(business); // Mapper에서 update 처리
  }

  //  // 사업자 계정 삭제
  @Override
  public boolean deleteBusinessAccount(String businessId, String password) {
    BusinessUserDTO business = mngMapper.selectBusinessById(businessId);
    if (business == null) return false;

    if (!password.equals(business.getBusinessPwd())) { // 평문 비교
      return false;
    }

    mngMapper.deleteBusiness(businessId); // Mapper에서 delete 처리
    return true;
  }

  // business_user 테이블의 restaurant_id 업데이트
  public void updateRestaurantIdForBusinessUser(String businessId, String restaurantId) {
    mngMapper.updateRestaurantIdForBusinessUser(businessId, restaurantId);
  }
}
