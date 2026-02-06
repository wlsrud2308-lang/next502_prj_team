package bitc.next502.team_prj.mapper;


import bitc.next502.team_prj.dto.ReservationDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationMapper {

    void insertReservation(ReservationDTO reservation);

}
