package com.CSV.test.repos;


import com.CSV.test.model.CsvModel;
import com.CSV.test.model.SimModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.UUID;

import java.text.SimpleDateFormat;

@Log4j2
@Repository
public class SimRepository {

    private final String unitInsert = "" +
        "INSERT INTO public.units(code_nomenclature, code_predefined, date_manufacture) " +
        "VALUES ('SIM', 'SIM', :dateManufacture::date) " +
        "RETURNING id";
    private final String simInsert = "" +
        "INSERT INTO public.sim_cards(id, code_operator, ip_sim, msisdn, iccid, code_predefined) " +
        "VALUES (:id, :codeOperator, :ipSim, :msisdn, :iccid, 'SIM')";


    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public SimRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void save(CsvModel simModel){
        MapSqlParameterSource params = new MapSqlParameterSource();

        try {
            params.addValue("dateManufacture", new SimpleDateFormat("dd.MM.yyyy").parse(simModel.getDateManufacture()) );
        }
        catch (ParseException e){
            log.error("Ошибка парсинга даты");
            return;
        }

        params.addValue("codeOperator", simModel.getCodeOperator());
        params.addValue("ipSim", simModel.getIpSim());
        params.addValue("msisdn", simModel.getMsisdn());
        params.addValue("iccid", simModel.getIccid());

        UUID idSim = jdbcTemplate.queryForObject(unitInsert, params, UUID.class);

        params.addValue("id", idSim);

        jdbcTemplate.update(simInsert, params);
    }
}
