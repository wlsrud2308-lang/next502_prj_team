package bitc.next502.team_prj.mapper;

import bitc.next502.team_prj.dto.BusinessUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BusinessUserMapper {

    BusinessUserDTO login(@Param("id") String id, @Param("pw") String pw);

    BusinessUserDTO findByNameAndPhone(@Param("name") String name, @Param("phone") String phone);

    BusinessUserDTO findByIdNamePhone(@Param("id") String id,
                                      @Param("name") String name,
                                      @Param("phone") String phone);
    //    회원가입
    void insertBusinessUser(BusinessUserDTO dto);

    //    비밀번호 변경
    int updatePassword(@Param("id") String id,
                       @Param("pw") String pw);

    int checkBusinessNameDuplicate(String businessName);

}