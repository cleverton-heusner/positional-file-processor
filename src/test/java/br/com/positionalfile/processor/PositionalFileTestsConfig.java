package br.com.positionalfile.processor;

import br.com.positionalfile.RecordLayout;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    protected List<? extends RecordLayout> getSublist(final Map<Class<?>, List<RecordLayout>> records,
                                                      final Class<? extends RecordLayout> recordClass,
                                                      final int toIndex) {

        return records.get(recordClass).subList(0, toIndex)
                .stream()
                .map(recordClass::cast)
                .collect(Collectors.toList());
    }
}
