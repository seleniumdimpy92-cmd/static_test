package com.example.utils;

import org.apache.commons.csv.*;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.*;
import java.util.stream.*;
import java.util.*;

public class CsvDataProvider {

    @DataProvider(name = "loginData")
    public static Object[][] loginData() throws IOException {
        Path p = Paths.get("src/test/resources/testdata/login-data.csv");
        try (Reader in = Files.newBufferedReader(p)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
            List<Object[]> data = new ArrayList<>();
            for (CSVRecord r : records) {
                data.add(new Object[] { r.get("username"), r.get("password"), r.get("expected") });
            }
            return data.toArray(new Object[0][]);
        }
    }
}
