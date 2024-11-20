package org.iffomko.server.repositories;

import org.iffomko.server.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByCustomerId(int customerId);
    void deleteAllByCustomerId(int customerId);
}
