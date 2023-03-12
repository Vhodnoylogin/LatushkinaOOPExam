package com.mpi.latushkina.exam.server.controllers;

import com.mpi.latushkina.exam.server.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
//@RequestMapping("/data")
public class DataController {

    @Autowired
    private DataService dataService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            dataService.processFile(file.getInputStream());
            String info = "Upload file with name - %s and length %s bytes";
            String name = file.getName();
            int size = file.getBytes().length;
            return ResponseEntity.ok().body(String.format(info, name, size));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/upload")
    public ResponseEntity<?> getInfo() {
        return ResponseEntity.ok().body("INFO");
    }

    @GetMapping("/findFault")
    public ResponseEntity<String> findFault(@RequestParam("startIndex") int startIndex, @RequestParam("endIndex") int endIndex) {
        String faultType = dataService.findFault(startIndex, endIndex);
        return ResponseEntity.ok(faultType);
    }

    @GetMapping("/saveSetPoint")
    public ResponseEntity<?> saveSetPoint(@RequestParam("setPoint") double setPoint) {
        dataService.setSetPoint(setPoint);
        return ResponseEntity.ok().build();
    }
}
