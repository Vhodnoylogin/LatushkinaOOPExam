package com.mpi.latushkina.exam.server.services;

import com.mpi.latushkina.exam.server.models.Measurement;
import com.mpi.latushkina.exam.server.models.Phases;
import com.mpi.latushkina.exam.server.repository.IDataRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DataService {
    protected static final Logger logger = LogManager.getLogger(DataService.class);

    private final IDataRepository dataRepository;
    private double maxCurrent = 0;

    @Autowired
    public DataService(IDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }


    public void processFile(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                logger.debug(line);
                Measurement measurement = parseMeasurement(line);
                logger.debug(measurement);
                if (measurement != null) {
                    dataRepository.addMeasurement(measurement);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to process file", e);
        }
    }

    public List<Measurement> getMeasurements() {
        return this.dataRepository.findMeasurements(-1000, 100000);
    }

    private Measurement parseMeasurement(String line) {
        String[] fields = line.split(",");
        if (fields.length < 4) {
            return null;
        }
        try {
            return new Measurement(
                    Double.parseDouble(fields[0]),
                    Double.parseDouble(fields[1]),
                    Double.parseDouble(fields[2]),
                    Double.parseDouble(fields[3])
            );
        } catch (Exception e) {
            return null;
        }

    }

    public double getMaxCurrent() {
        return this.maxCurrent;
    }

    public void setMaxCurrent(double maxCurrent) {
        this.maxCurrent = maxCurrent;
    }

    public String findFault(int startIndex, int endIndex) {
        try {
            var dataPoints = this.dataRepository.findMeasurements(startIndex, endIndex);

            if (dataPoints.isEmpty()) {
                return "Find no points";
            }

            List<Phases> phases = Arrays.asList(null, null, null);

            logger.info("this.maxCurrent = " + this.maxCurrent);
            dataPoints.stream()
                    .peek(x -> logger.info(x.getPhaseA() > this.maxCurrent))
                    .peek(x -> logger.info(x.getPhaseB() > this.maxCurrent))
                    .peek(x -> logger.info(x.getPhaseC() > this.maxCurrent))
                    .peek(x -> phases.set(0, x.getPhaseA() > this.maxCurrent ? Phases.A : phases.get(0)))
                    .peek(x -> phases.set(1, x.getPhaseB() > this.maxCurrent ? Phases.B : phases.get(1)))
                    .peek(x -> phases.set(2, x.getPhaseC() > this.maxCurrent ? Phases.C : phases.get(2)))
                    .collect(Collectors.toList());

            String res = phases.stream()
                    .filter(Objects::nonNull)
                    .map(Enum::name)
                    .collect(Collectors.joining(" "));
            if (res.trim().equals("")) return "No short circuit found";
            return res;
        } catch (Exception e) {
            logger.debug("ADASDADASDASDA!!!!!!!!!!!");
            logger.debug(e);
            throw e;
        }
    }
}
