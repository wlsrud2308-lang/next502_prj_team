package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.BusinessUserDTO;
import bitc.next502.team_prj.dto.NormalUserDTO;
import bitc.next502.team_prj.mapper.BusinessUserMapper;
import bitc.next502.team_prj.mapper.NormalUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class LoginServiceImpl implements LoginService {

    private final NormalUserMapper normalUserMapper;
    private final BusinessUserMapper businessUserMapper;

    public Object login(String id, String pw) {

        NormalUserDTO normalUser = normalUserMapper.login(id, pw);
        if (normalUser != null) {
            return normalUser;
        }

        BusinessUserDTO businessUser = businessUserMapper.login(id, pw);
        if (businessUser != null) {
            return businessUser;
        }

        return null;
    }
//  이름, 전화번호로 일반 아이디 조회
    public NormalUserDTO findNormalUserByNameAndPhone(String name, String phone) {
        return normalUserMapper.findByNameAndPhone(name, phone);
    }
//  이름, 전화번호로 사업자 아이디 조회
    public BusinessUserDTO findBusinessUserByNameAndPhone(String name, String phone) {
        return businessUserMapper.findByNameAndPhone(name, phone);
    }

    // 아이디+이름+전화번호로 일반 비밀번호 조회
    public NormalUserDTO findNormalUserByIdNamePhone(String id, String name, String phone) {
        return normalUserMapper.findByIdNamePhone(id, name, phone);
    }

    // 아이디+이름+전화번호로 사업자 비밀번호 조회
    public BusinessUserDTO findBusinessUserByIdNamePhone(String id, String name, String phone) {
        return businessUserMapper.findByIdNamePhone(id, name, phone);
    }

    @Override
    public boolean updatePassword(String userType, String userId, String newPassword) {

        if("NORMAL".equals(userType)){
            return normalUserMapper.updatePassword(userId, newPassword) > 0;
        }

        if("BUSINESS".equals(userType)){
            return businessUserMapper.updatePassword(userId, newPassword) > 0;
        }

        return false;
    }
}
