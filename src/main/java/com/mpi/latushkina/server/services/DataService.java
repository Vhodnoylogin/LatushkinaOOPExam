package com.mpi.latushkina.server.services;

import com.mpi.latushkina.server.models.Measurement;
import com.mpi.latushkina.server.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

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

    public void setSetPoint(double setPoint) {
        this.setPoint = setPoint;
    }

    public String findFault(int startIndex, int endIndex) {
        List<Measurement> measurements = dataRepository.findMeasurements(startIndex, endIndex);
        List<DataPoint> dataPoints = convertToDataPoints(measurements);
        List<DataPoint> filteredDataPoints = filterDataPoints(dataPoints);
        return detectFaultType(filteredDataPoints);
    }

    private Measurement parseMeasurement(String line) {
        // TODO: implement parsing of the measurement from the line
        return null;
    }

    private List<DataPoint> convertToDataPoints(List<Measurement> measurements) {
        // TODO: implement conversion of measurements to data points
        return null;
    }

    private List<DataPoint> filterDataPoints(List<DataPoint> dataPoints) {
        // TODO: implement filtering of data points based on the set point
        return null;
    }

    private String detectFaultType(List<DataPoint> dataPoints) {
        // TODO: implement detection of fault type based on filtered data points
        return "";
    }

}
