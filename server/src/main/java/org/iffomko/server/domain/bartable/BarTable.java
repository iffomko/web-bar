package org.iffomko.server.domain.bartable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BAR_TABLE")
@Getter
@Setter
public class BarTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "number")
    private int number;
    @Column(name = "capacity")
    @Enumerated(EnumType.STRING)
    private Capacity capacity;
}
