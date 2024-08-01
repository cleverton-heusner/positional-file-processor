package br.com.positionalfile.processor.writer;

import br.com.positionalfile.Record;
import br.com.positionalfile.formatter.Formatter;
import br.com.positionalfile.formatter.FormatterStrategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PositionalFileWriter<T extends Record> {

    private static final int RECORD_OFFSET = 0;

    private final FormatterStrategy formatterStrategy;
    private final SortedFieldCache sortedFieldCache;

    public PositionalFileWriter() {
        this.formatterStrategy = new FormatterStrategy();
        this.sortedFieldCache = new SortedFieldCache();
    }

    public void writeRecords(final List<T> entities, final String outputFilePath) {
        int recordsTotal = entities.size();
        try (final var writer = new BufferedWriter(new FileWriter(createOutputFilePath(outputFilePath)))) {
            for (int i = 0; i < entities.size(); i++) {
                try {
                    for (final Field field : sortedFieldCache.get(entities.get(i).getClass())) {
                        field.setAccessible(true);
                        boolean isFieldFormattingAnnotated = false;
                        final String fieldValue = String.valueOf(field.get(entities.get(i)));

                        for (final Annotation annotation : field.getAnnotations()) {
                            final Formatter formatter = formatterStrategy.select(annotation);
                            if (formatter != null) {
                                final String formattedValue = formatter.format(fieldValue);
                                writeFieldValue(writer, formattedValue);
                                isFieldFormattingAnnotated = true;
                                break;
                            }
                        }

                        if (!isFieldFormattingAnnotated) {
                            writeFieldValue(writer, fieldValue);
                        }
                    }

                    if (i < recordsTotal - 1) {
                        writer.newLine();
                    }
                } catch (final IllegalArgumentException | IllegalAccessException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (final IOException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    private File createOutputFilePath(final String outputFilePath) throws IOException {
        final Path outputPath = Paths.get(outputFilePath);
        Files.createDirectories(outputPath.getParent());

        return outputPath.toFile();
    }

    private void writeFieldValue(final BufferedWriter writer, final String value) throws IOException {
        writer.write(value, RECORD_OFFSET, value.length());
        writer.flush();
    }
}