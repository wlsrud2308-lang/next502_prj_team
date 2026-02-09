package bitc.next502.team_prj.mapper;

import bitc.next502.team_prj.dto.MngDTO;
import bitc.next502.team_prj.dto.MyResvDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MngMapper {

  MngDTO selectMngById(String businessId);

  List<MngDTO> selectResvList(String businessId);
}
