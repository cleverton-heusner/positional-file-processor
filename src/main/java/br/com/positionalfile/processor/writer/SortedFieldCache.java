package br.com.positionalfile.processor.writer;

import br.com.positionalfile.Record;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SortedFieldCache {

    private final Map<Class<? extends Record>, List<Field>> sortedFields;

    public SortedFieldCache() {
        this.sortedFields = new ConcurrentHashMap<>();
    }

    public List<Field> get(final Class<? extends Record> clazz) {
        return sortedFields.computeIfAbsent(clazz, key -> {
            final Field[] fields = key.getDeclaredFields();
            Arrays.sort(fields, Comparator.comparingInt(field -> {
                final Order order = field.getAnnotation(Order.class);
                return order != null ? order.value() : Integer.MAX_VALUE;
            }));

            return Arrays.asList(fields);
        });
    }
}