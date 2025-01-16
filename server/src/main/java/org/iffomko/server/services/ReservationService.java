package org.iffomko.server.services;

import org.iffomko.server.domain.Reservation;
import org.iffomko.server.exceptions.LocalizedException;
import org.iffomko.server.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private static final String YOU_ALREADY_RESERVED = "validation.reservation.you-already-reserved";
    private static final String USER_NOT_EXIST_MESSAGE = "validation.user.not-found";
    private static final String NOT_VALID_RESERVATION_MESSAGE = "validation.reservation.not-valid";
    private static final String TABLE_RESERVED_MESSAGE = "validation.reservation.table-reserved";

    private final UserService userService;
    private final ReservationRepository reservationRepository;

    public ReservationService(UserService userService,
                              ReservationRepository reservationRepository) {
        this.userService = userService;
        this.reservationRepository = reservationRepository;
    }

    public void save(Reservation reservation, String userPhone) {
        userService.byPhone(userPhone)
                .map(user -> {
                    if (isReserved(reservation, user.getId())) {
                        throw new LocalizedException(TABLE_RESERVED_MESSAGE);
                    }
                    reservation.setCustomerId(user.getId());
                    return reservationRepository.save(reservation);
                })
                .orElseThrow(() -> new LocalizedException(USER_NOT_EXIST_MESSAGE, userPhone));
    }

    public List<Reservation> byUserPhone(String phone) {
        return userService.byPhone(phone)
                .map(user -> reservationRepository.findAllByCustomerId(user.getId()))
                .orElseThrow(() -> new LocalizedException(USER_NOT_EXIST_MESSAGE, phone));
    }

    public void delete(int id) {
        reservationRepository.deleteById(id);
    }

    private boolean isReserved(Reservation reservation, int customerId) {
        if (reservation == null || reservation.getReservationDate() == null) {
            throw new LocalizedException(NOT_VALID_RESERVATION_MESSAGE);
        }
        Optional<Reservation> findReservation = reservationRepository.findByReservationDate(reservation.getReservationDate());
        if (findReservation.isPresent() && findReservation.get().getCustomerId() == customerId) {
            throw new LocalizedException(YOU_ALREADY_RESERVED);
        }
        return findReservation.isPresent();
    }
}
