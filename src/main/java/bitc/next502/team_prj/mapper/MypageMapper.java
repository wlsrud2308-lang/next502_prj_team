package bitc.next502.team_prj.mapper;

import bitc.next502.team_prj.dto.MyInfoDTO;
import bitc.next502.team_prj.dto.MyResvDTO;
import bitc.next502.team_prj.dto.NormalUserDTO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageMapper {
    // XML id="selectMyInfoById"
    MyInfoDTO selectMyInfoById(String userId);

    // XML id="selectMyResvList"
    //List<MyResvDTO> selectMyResvList(String userId);
    Page<MyResvDTO> selectMyResvList(String userId);

    // XML id="deleteReservation"
    int deleteReservation(int resvId);

    // 신규: 일반 유저 계정 조회
    NormalUserDTO selectNormalUserById(String userId);

    // 신규: 일반 유저 계정 삭제
    int deleteNormalUser(String userId);
}