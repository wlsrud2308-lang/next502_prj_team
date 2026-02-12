package bitc.next502.team_prj.mapper;

import bitc.next502.team_prj.dto.BusinessUserDTO;
import bitc.next502.team_prj.dto.ReviewDTO;
import bitc.next502.team_prj.dto.MngDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface MngMapper {

  MngDTO selectMngById(String businessId);

  List<MngDTO> selectResvList(String businessId);

  List<MngDTO> selectResvListByDate(String businessId, LocalDate resvDate);

  // 페이징 리뷰 조회
  List<ReviewDTO> selectReviewListByBusinessIdWithPaging(
      @Param("businessId") String businessId,
      @Param("offset") int offset,
      @Param("limit") int limit
  );

  // 총 리뷰 개수 조회
  int countReviewByBusinessId(@Param("businessId") String businessId);

  void updateReviewReply(@Param("reviewIdx") int reviewIdx,
                         @Param("replyContent") String replyContent);

  void updateReservationState(@Param("resvId") int resvId,
                              @Param("newState") String newState);

  List<MngDTO> selectResvListByDateExcludeCanceled(@Param("businessId") String businessId,
                                                   @Param("resvDate") LocalDate resvDate);
  void updateBusinessUserInfo(BusinessUserDTO userDTO);

  // 사업자 정보 조회
  BusinessUserDTO selectBusinessById(@Param("businessId") String businessId);

  // 사업자 정보 수정
  void updateBusiness(BusinessUserDTO business);

  // 사업자 계정 삭제
  void deleteReservations(@Param("businessId") String businessId);
  void deleteRestaurant(@Param("businessId") String businessId);
  void deleteBusiness(@Param("businessId") String businessId);

  void updateRestaurantIdForBusinessUser(@Param("businessId") String businessId,
                                         @Param("restaurantId") long restaurantId);
}
