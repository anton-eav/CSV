package com.CSV.test.repos;


import com.CSV.test.model.CsvTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsvRepo extends JpaRepository<CsvTemplate, String> {
}

