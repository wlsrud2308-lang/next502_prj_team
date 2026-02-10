package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.MngDTO;

import java.time.LocalDate;
import java.util.List;

public interface MngService {

  MngDTO getMngInfo(String businessId);

  List<MngDTO> getResvList(String businessId);

  List<MngDTO> getResvListByDate(String businessId, LocalDate resvDate);
}
