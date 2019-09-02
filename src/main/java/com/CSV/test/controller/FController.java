package com.CSV.test.controller;


import com.CSV.test.model.CsvTemplate;
import com.CSV.test.model.ValidMsg;
import com.CSV.test.repos.CsvRepo;
import com.CSV.test.serv.CsvServ;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@Log4j2
@RestController
public class  FController {

    @Autowired
    private CsvRepo csvRepo;

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    @PostMapping(value = "/read2", consumes = "multipart/form-data")
    public ResponseEntity mpf2 (@Valid @RequestParam("file") MultipartFile file) throws IOException {
        List<CsvTemplate> csvTemplates  =  CsvServ.csvServ(file.getInputStream());

        List<ValidMsg> errors_in_rows = new ArrayList<>();
        log.info("--- Start analysis ---");
        for (int i = 0; i < csvTemplates.size(); i++) {
            try {
                log.info("Analysis [{}] row with name [{}]", i, csvTemplates.get(i).getName());
                Set<ConstraintViolation<CsvTemplate>> violations = validator.validate(csvTemplates.get(i));
                if(violations.size() > 0) {
                    for (ConstraintViolation constraintViolation : violations) {
                        errors_in_rows.add(
                                new ValidMsg(
                                        (long)i,
                                        constraintViolation.getPropertyPath().toString(),
                                        constraintViolation.getMessage()
                                )
                        );
                        log.info("Поле [{}] не соответствует условию [{}]", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
                    }
                }
                else {log.info("Can save to DB");}
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        log.info("--- Finish analysis. Count errors [{}] ---", errors_in_rows.size());
        if(errors_in_rows.size() == 0) {
            log.info("--- Start save to DB ---");
            for (int i = 0; i < csvTemplates.size(); i++) {
                try {
                    csvRepo.save(csvTemplates.get(i));
                }
                catch (Exception e) {
                    errors_in_rows.add( new ValidMsg( (long)i, "", e.getMessage()));
                    log.error(e.getMessage());
                }
            }
            log.info("--- Finish save to DB ---");
            if(errors_in_rows.size() == 0) return ResponseEntity.ok(new ValidMsg( -1L, "", "Данных записано успешно ["+csvTemplates.size()+"]"));
            else return ResponseEntity.status(500).body(errors_in_rows);
        }else{
            return ResponseEntity.badRequest().body(errors_in_rows);

        }
    }
}
