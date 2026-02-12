package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.BusinessUserDTO;
import bitc.next502.team_prj.dto.MngDTO;
import bitc.next502.team_prj.dto.ReviewDTO;
import bitc.next502.team_prj.mapper.MngMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MngServiceImpl implements MngService {

  @Autowired
  private MngMapper mngMapper;

  @Override
  public MngDTO getMngInfo(String businessId) {
    return mngMapper.selectMngById(businessId);
  }

  @Override
  public List<MngDTO> getResvList(String businessId) {
    return mngMapper.selectResvList(businessId);
  }

  @Override
  public List<MngDTO> getResvListByDate(String businessId, LocalDate resvDate) {
    return mngMapper.selectResvListByDate(businessId, resvDate);
  }

  @Override
  public List<ReviewDTO> getReviewListByBusinessId(String businessId, int page, int size) {
    int offset = page * size;
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

  @Transactional  // 두 DELETE를 하나의 트랜잭션으로 묶음
  @Override
  public boolean deleteBusinessAccount(String businessId, String restaurantId, String password) {
    BusinessUserDTO user = mngMapper.selectBusinessById(businessId);
    if (user == null || !user.getBusinessPwd().equals(password)) {
      return false; // 비밀번호 틀림
    }

    // 레스토랑 먼저 삭제
    mngMapper.deleteRestaurant(restaurantId);
    // 사업자 계정 삭제
    mngMapper.deleteBusinessUser(businessId);

    return true;
  }

  @Override
  public void updateRestaurantIdForBusinessUser(String businessId, long restaurantId) {
    mngMapper.updateRestaurantIdForBusinessUser(businessId, restaurantId);
  }
}
