package com.mpi.latushkina.server.services;

import com.mpi.latushkina.server.models.Measurement;
import com.mpi.latushkina.server.models.Phases;
import com.mpi.latushkina.server.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataService {

    private final DataRepository dataRepository;
    private double setPoint = 0;

    @Autowired
    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void processFile(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                Measurement measurement = parseMeasurement(line);
                if (measurement != null) {
                    dataRepository.addMeasurement(measurement);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to process file", e);
        }
    }

    private Measurement parseMeasurement(String line) {
        String[] fields = line.split(",");
        if (fields.length < 4) {
            return null;
        }
        try {
            return new Measurement(
                    LocalDateTime.parse(fields[0]),
                    Double.parseDouble(fields[1]),
                    Double.parseDouble(fields[2]),
                    Double.parseDouble(fields[3])
            );
        } catch (Exception e) {
            return null;
        }

    }

    public void setSetPoint(double setPoint) {
        this.setPoint = setPoint;
    }

    public String findFault(int startIndex, int endIndex) {
        var dataPoints = dataRepository.findMeasurements(startIndex, endIndex);

        if (dataPoints.isEmpty()) {
            return "";
        }

        List<Phases> phases = Arrays.asList(null, null, null);
        dataPoints.stream()
                .peek(x -> phases.set(0, x.getPhaseA() > this.setPoint ? Phases.A : phases.get(0)))
                .peek(x -> phases.set(1, x.getPhaseB() > this.setPoint ? Phases.B : phases.get(1)))
                .peek(x -> phases.set(2, x.getPhaseC() > this.setPoint ? Phases.C : phases.get(2)))
                .count();

        return phases.stream()
                .map(Enum::name)
                .collect(Collectors.joining());
    }
}
