package org.iffomko.server.services;

import org.iffomko.server.domain.Customer;
import org.iffomko.server.domain.Reservation;
import org.iffomko.server.exceptions.LocalizedException;
import org.iffomko.server.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private static final String CUSTOMER_PHONE_ABSENT_MESSAGE = "reservation.validation.customer.phone-absent";
    private static final String CUSTOMER_NOT_EXIST_MESSAGE = "reservation.validation.customer.not-exist";

    private final CustomerService customerService;
    private final ReservationRepository reservationRepository;

    public ReservationService(CustomerService customerService,
                              ReservationRepository reservationRepository) {
        this.customerService = customerService;
        this.reservationRepository = reservationRepository;
    }

    public void save(Reservation reservation) {
        Customer customer = reservation.getCustomer();
        if (customer == null || customer.getPhone() == null) {
            throw new LocalizedException(CUSTOMER_PHONE_ABSENT_MESSAGE);
        }
        Optional<Customer> existCustomer = customerService.loadByPhone(customer.getPhone());
        if (existCustomer.isEmpty()) {
            reservation.getCustomer().setId(0);
            reservationRepository.save(reservation);
            return;
        }
        reservation.setCustomer(existCustomer.get());
        reservationRepository.save(reservation);
    }

    public List<Reservation> loadByCustomerPhone(String phone) { // todo: сделать валидацию номера
        Optional<Customer> loadedCustomer = customerService.loadByPhone(phone);
        if (loadedCustomer.isEmpty()) {
            throw new LocalizedException(CUSTOMER_NOT_EXIST_MESSAGE);
        }
        Customer customer = loadedCustomer.get();
        return reservationRepository.findAllByCustomerId(customer.getId());
    }

    public void deleteReservationByPhone(String phone) {
        Optional<Customer> loadedCustomer = customerService.loadByPhone(phone);
        if (loadedCustomer.isEmpty()) {
            throw new LocalizedException(CUSTOMER_NOT_EXIST_MESSAGE);
        }
        Customer customer = loadedCustomer.get();
        reservationRepository.deleteAllByCustomerId(customer.getId());
    }
}
