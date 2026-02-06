package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.MyInfoDTO;
import bitc.next502.team_prj.dto.MyResvDTO;
import bitc.next502.team_prj.mapper.MypageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageServiceImpl implements MypageService {

    @Autowired
    private MypageMapper myPageMapper; // 이 부분이 빠져 있어서 에러가 난 겁니다!

    // 내 정보 가져오기
    public MyInfoDTO getMyInfo(String userId) {
        return myPageMapper.selectMyInfoById(userId);
    }

    @Override
    public void editMyInfo(MyInfoDTO info) {
        myPageMapper.editMyInfo(info);
    }

    // 예약 목록 가져오기
    public List<MyResvDTO> getMyResvList(String userId) {
        return myPageMapper.selectMyResvList(userId);
    }

    // 예약 취소
    public void cancelReservation(int resvId) {
        myPageMapper.deleteReservation(resvId);
    }
}