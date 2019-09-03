package com.CSV.test.controller;


import com.CSV.test.model.ValidMsg;
import com.CSV.test.serv.SimService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.*;
import java.io.IOException;

@Log4j2
@RestController
public class SimController {

    @Autowired
    private SimService simService;

    @PostMapping(value = "/sim/import", consumes = "multipart/form-data")
    public ResponseEntity mpf2 (@Valid @RequestParam("file") MultipartFile file) {

        try {
            return simService.saveFile(file.getInputStream());
        }catch (IOException e){
            log.error("Ошибка чтения файла");
            return ResponseEntity.badRequest().body(new ValidMsg( -1L, "", "Ошибка чтения файла"));
        }
    }
}
