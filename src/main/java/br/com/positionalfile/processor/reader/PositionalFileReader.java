package br.com.positionalfile.processor.reader;

import br.com.positionalfile.Record;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PositionalFileReader {
    private final StringParserStrategy stringParserStrategy;

    public PositionalFileReader() {
        stringParserStrategy = new StringParserStrategy();
    }

    public List<Record> readRecords(final String inputFilePath, final Class<? extends Record> entityClass) {
        try (final FileReader fileReader = new FileReader(inputFilePath)) {
            return readRecords(fileReader, entityClass);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Record> readRecords(final InputStream inputFileStream, final Class<? extends Record> entityClass) {
        try (final InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(inputFileStream))) {
            return readRecords(inputStreamReader, entityClass);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<? extends Record> readRecords(final String inputFilePath,
                                              final Class<? extends Record> headerRecordClass,
                                              final Class<? extends Record> bodyRecordClass,
                                              final Class<? extends Record> footerRecordClass) {

        final int headerRecordSize = headerRecordClass.getAnnotation(Size.class).value();
        final int footerRecordSize = footerRecordClass.getAnnotation(Size.class).value();
        final List<String> records = readRecords(inputFilePath);
        int bodyRecordSize = records.size() - footerRecordSize;

        return mergeRecords(
                parseRecords(records.subList(0, headerRecordSize), headerRecordClass),
                parseRecords(records.subList(headerRecordSize, bodyRecordSize), bodyRecordClass),
                parseRecords(records.subList(bodyRecordSize, bodyRecordSize + footerRecordSize), footerRecordClass)
        );
    }

    public List<? extends Record> readRecords(final String inputFilePath,
                                              final Class<? extends Record> headerRecordClass,
                                              final Class<? extends Record> bodyRecordClass) {

        final int headerRecordSize = headerRecordClass.getAnnotation(Size.class).value();
        final List<String> records = readRecords(inputFilePath);

        return mergeRecords(
                parseRecords(records.subList(0, headerRecordSize), headerRecordClass),
                parseRecords(records.subList(headerRecordSize, records.size()), bodyRecordClass)
        );
    }

    private List<String> readRecords(final String inputFilePath) {
        final List<String> records = new ArrayList<>();
        try (final BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String currentRecord;
            while ((currentRecord = br.readLine()) != null) {
                records.add(currentRecord);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return records;
    }

    @SafeVarargs
    private List<? extends Record> mergeRecords(final List<? extends Record> ...records) {
        return Stream.of(records).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private List<Record> parseRecords(final List<String> records, final Class<? extends Record> recordClass) {
        return records.stream().map(r -> parseRecord(r, recordClass)).collect(Collectors.toList());
    }

    private Record parseRecord(final String record, final Class<? extends Record> recordClass) {
        final Record typedRecord;
        try {
            typedRecord = recordClass.getDeclaredConstructor().newInstance();
            for (final Field field : recordClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(FieldPosition.class)) {
                    final Object fieldValue = retrieveFieldValue(field, record);
                    setFieldValue(field, recordClass, typedRecord, fieldValue);
                }
            }
        } catch (final InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return typedRecord;
    }

    private Object retrieveFieldValue(final Field field, final String record) {
        final FieldPosition fieldPosition = field.getAnnotation(FieldPosition.class);
        final String fieldValue = record.substring(fieldPosition.begin(), fieldPosition.end());

        return stringParserStrategy.select(field.getType()).apply(fieldValue);
    }

    private void setFieldValue(final Field field, final Class<? extends Record> entityClass, final Record entity, final Object fieldValue) {
        final Method setter;
        try {
            setter = new PropertyDescriptor(field.getName(), entityClass).getWriteMethod();
            setter.invoke(entity, fieldValue);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Record> readRecords(final Reader reader, final Class<? extends Record> entityClass) {
        final List<Record> allRecords = new ArrayList<>();
        try (final BufferedReader br = new BufferedReader(reader)) {
            String record;
            while ((record = br.readLine()) != null) {
                allRecords.add(parseRecord(record, entityClass));
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return allRecords;
    }
}