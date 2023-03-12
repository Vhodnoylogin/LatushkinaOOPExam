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
                return "";
            }

            List<Phases> phases = Arrays.asList(null, null, null);

//            Map<Phases,Phases> phases =new EnumMap<>(Phases.class);
            logger.info("phases 1 = " + phases);

//            dataPoints.stream()
//                    .peek(logger::info)
//                    .peek(x-> phases.put(
//                            Phases.A
//                            , phases.getOrDefault(Phases.A, x.getPhaseA() > this.maxCurrent ? Phases.A : null)
//                    ))
//                    .peek(x-> phases.put(
//                            Phases.B
//                            , phases.getOrDefault(Phases.B, x.getPhaseB() > this.maxCurrent ? Phases.B : null)
//                    ))
//                    .peek(x-> phases.put(
//                            Phases.C
//                            , phases.getOrDefault(Phases.C, x.getPhaseC() > this.maxCurrent ? Phases.C : null)
//                    ))
//                    .collect(Collectors.toList());
            dataPoints.forEach(x -> {
//                logger.info(x);
                if (x.getPhaseA() > this.maxCurrent) phases.set(0, Phases.A);
                if (x.getPhaseB() > this.maxCurrent) phases.set(1, Phases.B);
                if (x.getPhaseC() > this.maxCurrent) phases.set(2, Phases.C);
            });
            logger.info("this.maxCurrent = " + this.maxCurrent);
            logger.info("phases 2 = " + phases);

            String res = phases.stream()
//                    .peek(logger::info)
                    .filter(Objects::nonNull)
                    .map(Enum::name)
                    .collect(Collectors.joining(" "))
                    .trim();
            if (res.trim().equals("")) return "";
            return res;
        } catch (Exception e) {
            logger.debug("ADASDADASDASDA!!!!!!!!!!!");
            logger.debug(e);
            throw e;
        }
    }
}
