package bitc.next502.team_prj.service;

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
  public List<ReviewDTO> getReviewListByBusinessId(String businessId) {
    return mngMapper.selectReviewListByBusinessId(businessId);
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

}
