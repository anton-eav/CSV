package com.CSV.test.serv;

import com.CSV.test.model.CsvTemplate;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CsvServ {

    public static List<CsvTemplate> csvServ(InputStream stream) throws IOException {

        CsvMapper mapper = new CsvMapper();

        CsvSchema schema = mapper.schemaFor(CsvTemplate.class).withHeader().withColumnReordering(true).withColumnSeparator('\t');

        ObjectReader reader = mapper.readerFor(CsvTemplate.class).with(schema);

        return reader.<CsvTemplate>readValues(stream).readAll();
    }
}
