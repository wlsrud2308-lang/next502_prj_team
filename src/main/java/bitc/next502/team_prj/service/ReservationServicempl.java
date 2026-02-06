package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.ReservationDTO;
import bitc.next502.team_prj.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationServicempl implements ReservationService {

    private final ReservationMapper reservationMapper;

    @Override
    public void saveReservation(ReservationDTO reservation) {

        if (reservation.getReservationState() == null) {
            reservation.setReservationState("방문예정");
        }

        reservationMapper.insertReservation(reservation);
    }
}