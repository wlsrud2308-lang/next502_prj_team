package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.MngDTO;
import bitc.next502.team_prj.dto.MyResvDTO;
import bitc.next502.team_prj.mapper.MngMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MngServiceImpl implements MngService{

  @Autowired
  private MngMapper mngMapper;

  @Override
  public MngDTO getMngInfo(String businessId) {
    return mngMapper.selectMngById(businessId);
  }

  @Override
  public List<MngDTO> getResvList(String businessID) {
      return mngMapper.selectResvList(businessID);
  }

  @Override
  public List<MngDTO> getResvList(LocalDate resvDate) {
    return mngMapper.selectList(resvDate);
  }
}
