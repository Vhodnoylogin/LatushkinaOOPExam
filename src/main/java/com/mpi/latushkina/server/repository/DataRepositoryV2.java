package com.mpi.latushkina.server.repository;

import com.mpi.latushkina.server.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataRepositoryV2 extends JpaRepository<Measurement, Long> {

    void saveMeasurement(Measurement measurement);

    @Query("SELECT m FROM Measurement m WHERE m.index >= :startIndex AND m.index <= :endIndex")
    List<Measurement> findMeasurementsInRange(@Param("startIndex") int startIndex, @Param("endIndex") int endIndex);

}
