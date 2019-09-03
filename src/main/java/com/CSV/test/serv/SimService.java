package com.CSV.test.serv;

import com.CSV.test.model.SimModel;
import com.CSV.test.model.ValidMsg;
import com.CSV.test.repos.SimRepository;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Log4j2
@Service
public class SimService {

    @Autowired
    SimRepository simRepository;

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    public ResponseEntity saveFile(InputStream stream){

        // Чтение файла и проверка, что он не пустой
        List<SimModel> simsFromCsv = fileToList(stream);
        if(simsFromCsv.size() == 0) return ResponseEntity.badRequest().body(new ValidMsg( -1L, "", "Файл пуст или ошибка парсинга файла"));

        // Массив для хранения ошибок
        List<ValidMsg> errors_in_rows = new ArrayList<>();

        log.info("--- Start analysis ---");
        for (int i = 0; i < simsFromCsv.size(); i++) {
            try {
                log.info("Analysis [{}] row", i);
                Set<ConstraintViolation<SimModel>> violations = validator.validate(simsFromCsv.get(i));
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
            for (int i = 0; i < simsFromCsv.size(); i++) {
                try {
                    // TODO СМОТРИ СЮДА, ВЫЗОВ РЕПОЗИТОРИЯ ДЛЯ СОХРАНЕНИЯ ДАННЫХ
                    // TODO ПАРАМЕТРОМ ПЕРЕДАЕТСЯ ОДИН ОБЪЕКТ (ОДНА СТРОКА ИЗ CSV)
                    simRepository.save(simsFromCsv.get(i));
                }
                catch (Exception e) {
                    errors_in_rows.add( new ValidMsg( (long)i, "", e.getMessage()));
                    log.error(e.getMessage());
                }
            }
            log.info("--- Finish save to DB ---");
            if(errors_in_rows.size() == 0) return ResponseEntity.ok(new ValidMsg( -1L, "", "Данных записано успешно ["+simsFromCsv.size()+"]"));
            else return ResponseEntity.status(500).body(errors_in_rows);
        }else{
            return ResponseEntity.badRequest().body(errors_in_rows);

        }
    }

    private List<SimModel> fileToList(InputStream stream){

        CsvMapper mapper = new CsvMapper();

        CsvSchema schema = mapper.schemaFor(SimModel.class).withHeader().withColumnReordering(true).withColumnSeparator(';');

        ObjectReader reader = mapper.readerFor(SimModel.class).with(schema);
        try {
            return reader.<SimModel>readValues(stream).readAll();
        }catch (IOException e){
            log.error("Ошибка парсинга файла");
            return new ArrayList<>();
        }

    }
}
