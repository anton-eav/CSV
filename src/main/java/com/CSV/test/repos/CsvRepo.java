package com.CSV.test.repos;


import com.CSV.test.model.CsvTemplate;
import org.springframework.data.repository.CrudRepository;

public interface CsvRepo extends CrudRepository<CsvTemplate, Integer> {
}

