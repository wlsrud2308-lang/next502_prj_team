package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.BusinessUserDTO;
import bitc.next502.team_prj.dto.NormalUserDTO;

public interface SignupService {

    // 일반회원 가입
    void signupNormalUser(NormalUserDTO userDTO);

    // 사업자 회원 가입
    void signupBusinessUser(BusinessUserDTO userDTO);
}