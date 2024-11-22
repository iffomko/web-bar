package org.iffomko.server.services;

import org.iffomko.server.domain.User;
import org.iffomko.server.domain.Reservation;
import org.iffomko.server.exceptions.LocalizedException;
import org.iffomko.server.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private static final String USER_PHONE_ABSENT_MESSAGE = "validation.user.phone-absent";
    private static final String USER_NOT_EXIST_MESSAGE = "validation.user.not-found";

    private final UserService userService;
    private final ReservationRepository reservationRepository;

    public ReservationService(UserService userService,
                              ReservationRepository reservationRepository) {
        this.userService = userService;
        this.reservationRepository = reservationRepository;
    }

    public void save(Reservation reservation) {
        User user = reservation.getUser();
        if (user == null || user.getPhone() == null) {
            throw new LocalizedException(USER_PHONE_ABSENT_MESSAGE);
        }
        userService.byPhone(user.getPhone())
                .map(realUser -> {
                    reservation.setUser(realUser);
                    return reservationRepository.save(reservation);
                })
                .orElseThrow(() -> new LocalizedException(USER_NOT_EXIST_MESSAGE, user.getPhone()));
    }

    public List<Reservation> byUserPhone(String phone) { // todo: сделать валидацию номера
        return userService.byPhone(phone)
                .map(user -> reservationRepository.findAllByUserId(user.getId()))
                .orElseThrow(() -> new LocalizedException(USER_NOT_EXIST_MESSAGE, phone));
    }

    public void deleteByUserPhone(String phone) {
        userService.byPhone(phone)
                .map(user -> {
                    reservationRepository.deleteAllByUserId(user.getId());
                    return true;
                })
                .orElseThrow(() -> new LocalizedException(USER_NOT_EXIST_MESSAGE, phone));
    }
}
