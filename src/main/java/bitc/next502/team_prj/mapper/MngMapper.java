package bitc.next502.team_prj.mapper;

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

  List<ReviewDTO> selectReviewListByBusinessId(String businessId);

  void updateReviewReply(@Param("reviewIdx") int reviewIdx,
                         @Param("replyContent") String replyContent);

  void updateReservationState(@Param("resvId") int resvId,
                              @Param("newState") String newState);

  List<MngDTO> selectResvListByDateExcludeCanceled(@Param("businessId") String businessId,
                                                   @Param("resvDate") LocalDate resvDate);

}
