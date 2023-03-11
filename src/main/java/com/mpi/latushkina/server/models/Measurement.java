package com.mpi.latushkina.server.models;

import jakarta.persistence.*;

@Entity
@Table(name = "measurements")
public record Measurement(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        String timestamp,
        Double ia,
        Double ib,
        Double ic
) {
}
