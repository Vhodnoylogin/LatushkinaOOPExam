package com.mpi.latushkina.exam.server.repository;

import com.mpi.latushkina.exam.server.models.Measurement;

import java.util.List;

public interface IDataRepository {
    void addMeasurement(Measurement measurement);

    List<Measurement> findMeasurements(int startIndex, int endIndex);


}
