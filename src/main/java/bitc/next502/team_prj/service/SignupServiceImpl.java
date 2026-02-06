package bitc.next502.team_prj.service;
import bitc.next502.team_prj.dto.BusinessUserDTO;
import bitc.next502.team_prj.dto.NormalUserDTO;
import bitc.next502.team_prj.mapper.BusinessUserMapper;
import bitc.next502.team_prj.mapper.NormalUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupServiceImpl {

    private final NormalUserMapper normalUserMapper;
    private final BusinessUserMapper businessUserMapper;

    // 일반회원 가입
    public void signupNormalUser(NormalUserDTO userDTO) {

        // 권한 기본값
        userDTO.setUserRole(0);

        normalUserMapper.insertNormalUser(userDTO);
    }

    // 식당 관리자 가입
    public void signupBusinessUser(BusinessUserDTO userDTO) {

        businessUserMapper.insertBusinessUser(userDTO);
    }
}
