package com.CSV.test.controller;


import com.CSV.test.model.CsvTemplate;
import com.CSV.test.repos.CsvRepo;
import com.CSV.test.serv.CsvServ;
import lombok.extern.log4j.Log4j2;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
public class FController {

    @Autowired
    private CsvRepo csvRepo;

//    @Autowired
//    @Qualifier("validator")
//    private Validator validator;

//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.setValidator(validator);
//    }
    static final Logger rootLogger = LogManager.getRootLogger();
    static final Logger userLogger = LogManager.getLogger(CsvTemplate.class);


    private Map<String, CsvTemplate> csvTemplateMap = null;
    public FController(){csvTemplateMap = new HashMap<String, CsvTemplate>(); }


    @PostMapping(value = "/readf", consumes = "text/csv")
    public void sf (@RequestBody InputStream stream)throws IOException {
        csvRepo.saveAll(CsvServ.csvServ(stream));
    }


        @PostMapping(value = "/read", consumes = "multipart/form-data")
    public ResponseEntity mpf (@Valid @RequestParam("file") MultipartFile file) throws IOException {
            int count_error_rows = 0;

            for (int i = 0; i < CsvServ.csvServ(file.getInputStream()).size(); i++) {
                try {
                    csvRepo.save(CsvServ.csvServ(file.getInputStream()).get(i));
                } catch (Exception e) {
                    count_error_rows++;
                }
            }
            if (count_error_rows > 0) return ResponseEntity.badRequest().body(count_error_rows);
            else return ResponseEntity.ok(count_error_rows);

        }

    @PostMapping(value = "/read2", consumes = "multipart/form-data2")
    public ResponseEntity mpf2 (@Valid @RequestParam("file") MultipartFile file) throws IOException {
        int count_error_rows = 0;
        List<CsvTemplate> csvTemplates = new ArrayList<>();
        for (int i = 0; i < CsvServ.csvServ(file.getInputStream()).size(); i++) {
            try {

                csvRepo.save(CsvServ.csvServ(file.getInputStream()).get(i));
            } catch (Exception e) {
                csvTemplates.add( CsvServ.csvServ(file.getInputStream()).get(i));
                count_error_rows++;
                System.out.println(csvTemplates);
            }
        }
        if (count_error_rows > 0) return ResponseEntity.badRequest().body(count_error_rows);
        else return ResponseEntity.ok(count_error_rows);
    }
}
