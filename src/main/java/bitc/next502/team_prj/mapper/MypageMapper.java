package bitc.next502.team_prj.mapper;

import bitc.next502.team_prj.dto.MyInfoDTO;
import bitc.next502.team_prj.dto.MyResvDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageMapper {
    // XML id="selectMyInfoById"
    MyInfoDTO selectMyInfoById(String userId);

    // 내 정보 수정
    void editMyInfo(MyInfoDTO info);

    // XML id="selectMyResvList"
    List<MyResvDTO> selectMyResvList(String userId);

    // XML id="deleteReservation"
    int deleteReservation(int resvId);
}