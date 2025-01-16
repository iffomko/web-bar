package org.iffomko.server.controllers;

import org.iffomko.server.domain.Reservation;
import org.iffomko.server.services.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.iffomko.server.domain.ControllerNames.RESERVATION_URL;

@RestController
@RequestMapping(RESERVATION_URL)
@CrossOrigin("http://localhost:4200/")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public void makeReservation(@RequestBody Reservation reservation, @RequestParam String userPhone) {
        reservationService.save(reservation, userPhone);
    }

    @GetMapping
    public List<Reservation> loadReservations(@RequestParam("userPhone") String phone) {
        return reservationService.byUserPhone(phone);
    }

    @DeleteMapping
    public void deleteReservation(@RequestParam("id") int id) {
        reservationService.delete(id);
    }
}
