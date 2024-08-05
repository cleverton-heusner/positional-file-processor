package br.com.positionalfile.processor.reader;

import br.com.positionalfile.RecordLayout;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PositionalFileReader {
    private final StringParserStrategy stringParserStrategy;
    private final DelimiterMatcherStrategy delimiterMatcherStrategy;

    public PositionalFileReader() {
        stringParserStrategy = new StringParserStrategy();
        delimiterMatcherStrategy = new DelimiterMatcherStrategy();
    }

    @SafeVarargs
    public final Map<Class<?>, List<RecordLayout>> readRecords(final InputStream inputStream,
                                                               final Class<? extends RecordLayout>... recordClasses) {

        try (final InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
            return readRecords(inputStreamReader, recordClasses);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SafeVarargs
    public final Map<Class<?>, List<RecordLayout>> readRecords(final String inputFilePath,
                                                               final Class<? extends RecordLayout>... recordClasses) {

        try (final FileReader fileReader = new FileReader(inputFilePath)) {
            return readRecords(fileReader, recordClasses);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SafeVarargs
    private Map<Class<?>, List<RecordLayout>> readRecords(final Reader reader,
                                                          final Class<? extends RecordLayout>... recordClasses) {

        final List<RecordLayout> allRecordLayouts = new ArrayList<>();
        try (final BufferedReader bufferedReader = new BufferedReader(reader)) {
            Class<? extends RecordLayout> currentLayout = recordClasses[0];
            String currentDelimiter = getDelimiterValue(recordClasses[0]);
            Matcher currentMatcher = getDelimiterMatcher(recordClasses[0]);
            String record;

            while ((record = bufferedReader.readLine()) != null) {
                boolean isMatchedDelimiter = delimiterMatcherStrategy.select(currentMatcher).apply(record, currentDelimiter);
                for (int i = 0; i < recordClasses.length; i++) {
                    if (currentLayout.equals(recordClasses[i])) {
                        if ((i == recordClasses.length - 1 && currentDelimiter.isEmpty()) || isMatchedDelimiter) {
                            allRecordLayouts.add(parseRecord(record, recordClasses[i]));
                        }
                        else {
                            currentLayout = recordClasses[i + 1];
                            currentDelimiter = getDelimiterValue(currentLayout);
                            currentMatcher = getDelimiterMatcher(currentLayout);
                            isMatchedDelimiter = delimiterMatcherStrategy.select(currentMatcher).apply(record, currentDelimiter);
                        }
                    }
                }
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return allRecordLayouts.stream().collect(Collectors.groupingBy(Object::getClass));
    }

    private String getDelimiterValue(final Class<? extends RecordLayout> recordClass) {
        return recordClass.getAnnotation(Delimiter.class).value();
    }

    private Matcher getDelimiterMatcher(final Class<? extends RecordLayout> recordClass) {
        return recordClass.getAnnotation(Delimiter.class).matcher();
    }

    private RecordLayout parseRecord(final String record, final Class<? extends RecordLayout> recordClass) {
        final RecordLayout typedRecordLayout;
        try {
            typedRecordLayout = recordClass.getDeclaredConstructor().newInstance();
            for (final Field field : recordClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(FieldPosition.class)) {
                    final Object fieldValue = retrieveFieldValue(field, record);
                    setFieldValue(field, recordClass, typedRecordLayout, fieldValue);
                }
            }
        } catch (final InstantiationException | IllegalAccessException | InvocationTargetException |
                       NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return typedRecordLayout;
    }

    private Object retrieveFieldValue(final Field field, final String record) {
        final FieldPosition fieldPosition = field.getAnnotation(FieldPosition.class);
        final String fieldValue = record.substring(fieldPosition.begin(), fieldPosition.end());

        return stringParserStrategy.select(field.getType()).apply(fieldValue);
    }

    private void setFieldValue(final Field field,
                               final Class<? extends RecordLayout> entityClass,
                               final RecordLayout entity,
                               final Object fieldValue) {
        final Method setter;
        try {
            setter = new PropertyDescriptor(field.getName(), entityClass).getWriteMethod();
            setter.invoke(entity, fieldValue);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}