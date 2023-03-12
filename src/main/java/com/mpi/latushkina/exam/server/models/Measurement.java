package com.mpi.latushkina.exam.server.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "measurements")
public class Measurement {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private LocalDateTime dateTime;
        private double phaseA;
        private double phaseB;
        private double phaseC;

        public Measurement(LocalDateTime dateTime, double phaseA, double phaseB, double phaseC) {
                this.dateTime = dateTime;
                this.phaseA = phaseA;
                this.phaseB = phaseB;
                this.phaseC = phaseC;
        }

        public double getPhaseB() {
                return phaseB;
        }

        public void setPhaseB(double phaseB) {
                this.phaseB = phaseB;
        }

        public double getPhaseC() {
                return phaseC;
        }

        public void setPhaseC(double phaseC) {
                this.phaseC = phaseC;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public LocalDateTime getDateTime() {
                return dateTime;
        }

        public void setDateTime(LocalDateTime dateTime) {
                this.dateTime = dateTime;
        }

        public double getPhaseA() {
                return phaseA;
        }

        public void setPhaseA(double phaseA) {
                this.phaseA = phaseA;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Measurement that = (Measurement) o;
                return Double.compare(that.phaseA, phaseA) == 0 &&
                        Objects.equals(id, that.id) &&
                        Objects.equals(dateTime, that.dateTime);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, dateTime, phaseA);
        }

        @Override
        public String toString() {
                return "Measurement{" +
                        "id=" + id +
                        ", dateTime=" + dateTime +
                        ", value=" + phaseA +
                        '}';
        }
}
