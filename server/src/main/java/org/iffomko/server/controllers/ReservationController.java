package org.iffomko.server.controllers;

import org.iffomko.server.domain.Reservation;
import org.iffomko.server.services.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.iffomko.server.domain.ControllerNames.CUSTOMERS_URI_PART;
import static org.iffomko.server.domain.ControllerNames.RESERVATION_URL;

@RestController
@RequestMapping(RESERVATION_URL)
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public void makeReservation(Reservation reservation) {
        reservationService.save(reservation);
    }

    @GetMapping(CUSTOMERS_URI_PART)
    public List<Reservation> loadReservations(@RequestParam("phone") String phone) {
        return reservationService.loadByCustomerPhone(phone);
    }

    @DeleteMapping(CUSTOMERS_URI_PART)
    public void deleteReservation(@RequestParam("phone") String phone) {
        reservationService.deleteReservationByPhone(phone);
    }
}
