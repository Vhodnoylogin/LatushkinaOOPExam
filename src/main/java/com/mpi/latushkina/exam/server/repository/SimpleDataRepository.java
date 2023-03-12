//package com.mpi.latushkina.exam.server.repository;
//
//import com.mpi.latushkina.exam.server.models.Measurement;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Repository
//public class SimpleDataRepository implements IDataRepository {
//    protected final List<Measurement> repo = new ArrayList<>();
//
//    @Override
//    public void addMeasurement(Measurement measurement) {
//        this.repo.add(measurement);
//    }
//
//    @Override
//    public List<Measurement> findMeasurements(int startIndex, int endIndex) {
//        return this.repo.stream()
//                .filter(x -> x.getId() >= startIndex)
//                .filter(x -> x.getId() <= endIndex)
//                .collect(Collectors.toList());
//    }
//}
