package com.mpi.latushkina.exam.server.controllers;

import com.mpi.latushkina.exam.server.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService dataService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            dataService.processFile(file.getInputStream());
            final String info = "Upload file with name - %s and length %s bytes";
//            return ResponseEntity.ok().body(String.format(info, file.getName(), file.getBytes().length));
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/findFault")
    public ResponseEntity<String> findFault(@RequestParam("startIndex") int startIndex, @RequestParam("endIndex") int endIndex) {
        String faultType = dataService.findFault(startIndex, endIndex);
        return ResponseEntity.ok().body(faultType);
//        return ResponseEntity.ok().build();
    }

    @GetMapping("/saveSetPoint")
    public ResponseEntity<?> saveSetPoint(@RequestParam("setPoint") double maxCurrent) {
        dataService.setMaxCurrent(maxCurrent);
//        return ResponseEntity.ok().body("set max current at " + dataService.getMaxCurrent());
        return ResponseEntity.ok().build();
    }
}
