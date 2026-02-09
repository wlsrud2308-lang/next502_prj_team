package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.MngDTO;
import bitc.next502.team_prj.dto.MyResvDTO;

import java.util.List;

public interface MngService {

  MngDTO getMngInfo(String businessId);

  List<MngDTO> getResvList(String businessID);
}
