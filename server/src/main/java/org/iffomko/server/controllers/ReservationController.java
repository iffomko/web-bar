package org.iffomko.server.controllers;

import org.iffomko.server.domain.Reservation;
import org.iffomko.server.services.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.iffomko.server.domain.ControllerNames.USERS_URI_PART;
import static org.iffomko.server.domain.ControllerNames.RESERVATION_URL;

@RestController
@RequestMapping(RESERVATION_URL)
@CrossOrigin("/**")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public void makeReservation(Reservation reservation) {
        reservationService.save(reservation);
    }

    @GetMapping(USERS_URI_PART)
    public List<Reservation> loadReservations(@RequestParam("phone") String phone) {
        return reservationService.byUserPhone(phone);
    }

    @DeleteMapping(USERS_URI_PART)
    public void deleteReservation(@RequestParam("phone") String phone) {
        reservationService.deleteByUserPhone(phone);
    }
}
