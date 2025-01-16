package org.iffomko.server.repositories;

import org.iffomko.server.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByCustomerId(int customerId);

    void deleteById(int id);

    Optional<Reservation> findByReservationDate(Instant reservationDate);
}
