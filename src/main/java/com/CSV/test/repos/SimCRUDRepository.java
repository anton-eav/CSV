package com.CSV.test.repos;

import com.CSV.test.model.SimModel;
import com.CSV.test.model.UnitModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SimCRUDRepository extends CrudRepository<SimModel, UUID> {
}
