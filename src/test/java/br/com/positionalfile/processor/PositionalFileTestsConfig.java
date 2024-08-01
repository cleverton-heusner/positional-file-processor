package br.com.positionalfile.processor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PositionalFileTestsConfig {

    protected List<String> readRecords(final String outputFilePath) throws IOException {
        final List<String> records = new ArrayList<>();

        try(final BufferedReader br = new BufferedReader(new FileReader(outputFilePath))) {
            String record;
            while ((record = br.readLine()) != null) {
                records.add(record);
            }
        }

        return records;
    }
}
