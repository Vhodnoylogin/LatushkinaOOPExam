package com.mpi.latushkina.exam.server.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "measurements")
public class Measurement {

        protected static Long it = 1L;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private double milliSeconds;
        private double phaseA;
        private double phaseB;
        private double phaseC;

        public Measurement() {

        }

        public Measurement(double milliSeconds, double phaseA, double phaseB, double phaseC) {
                this.milliSeconds = milliSeconds;
                this.phaseA = phaseA;
                this.phaseB = phaseB;
                this.phaseC = phaseC;
                this.id = it++;
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

        public double getMilliSeconds() {
                return milliSeconds;
        }

        public void setMilliSeconds(double milliSeconds) {
                this.milliSeconds = milliSeconds;
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
                        Objects.equals(milliSeconds, that.milliSeconds);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, milliSeconds, phaseA);
        }

        @Override
        public String toString() {
                return "Measurement{" +
                        "id=" + id +
//                        ", milliSeconds=" + milliSeconds +
                        ", phaseA=" + phaseA +
                        ", phaseB=" + phaseB +
                        ", phaseC=" + phaseC +
                        '}';
        }
}
