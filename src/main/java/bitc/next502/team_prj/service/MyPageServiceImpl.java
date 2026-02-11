package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.MyInfoDTO;
import bitc.next502.team_prj.dto.MyResvDTO;
import bitc.next502.team_prj.dto.NormalUserDTO;
import bitc.next502.team_prj.mapper.MypageMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

    // 예약 목록 가져오기
//    //public List<MyResvDTO> getMyResvList(String userId) {
//        return myPageMapper.selectMyResvList(userId);
//    }

    // 예약 목록 가져오기 -페이지포함
    public Page<MyResvDTO> getMyResvList(int pageNum, String userId) {

        int pageSize = 5; //화면에 보여줄 row의 수
        PageHelper.startPage(pageNum, pageSize);
        return myPageMapper.selectMyResvList(userId);
    }
        // 예약 취소
    public void cancelReservation(int resvId) {
        myPageMapper.deleteReservation(resvId);
    }

    public boolean deleteNormalUserAccount(String userId, String password) {
        // 유저 정보 조회
        NormalUserDTO user = myPageMapper.selectNormalUserById(userId);
        if (user == null) return false;

        // 비밀번호 확인
        if (!password.equals(user.getUserPw())) { // 평문 비교
            return false;
        }

        // 삭제 처리
        myPageMapper.deleteNormalUser(userId);
        return true;
    }
}