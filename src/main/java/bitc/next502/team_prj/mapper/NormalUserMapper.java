package bitc.next502.team_prj.mapper;

import bitc.next502.team_prj.dto.NormalUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NormalUserMapper {

    NormalUserDTO login(@Param("id") String id, @Param("pw") String pw);

    NormalUserDTO findByNameAndPhone(@Param("name") String name, @Param("phone") String phone);

    NormalUserDTO findByIdNamePhone(@Param("id") String id, @Param("name") String name, @Param("phone") String phone);

    //    회원가입
    void insertNormalUser(NormalUserDTO dto);

//    비밀번호 변경
    int updatePassword(@Param("id") String id,
                       @Param("pw") String pw);
}
