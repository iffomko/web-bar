package org.iffomko.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.iffomko.server.domain.bartable.BarTable;

import java.time.Instant;

@Entity
@Table(name = "RESERVATIONS")
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "bar_table_id", nullable = false)
    private BarTable table;
    @Column(name = "reservation_date")
    private Instant reservationDate;
    @Column(name = "table_reservation_date")
    private Instant tableReservationDate;
}
