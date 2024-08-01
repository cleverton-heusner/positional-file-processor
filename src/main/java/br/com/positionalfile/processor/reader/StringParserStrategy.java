package br.com.positionalfile.processor.reader;

import java.util.function.Function;

public class StringParserStrategy {

    public Function<String, Object> select(final Class<?> clazz) {
        if (clazz.equals(Integer.class)) {
            return Integer::parseInt;
        }
        else if (clazz.equals(Double.class)) {
            return Double::parseDouble;
        }
        else if (clazz.equals(Boolean.class)) {
            return Boolean::parseBoolean;
        }
        else if (clazz.equals(String.class)) {
            return String::valueOf;
        }
        else if (clazz.equals(Character.class)) {
            return str -> str.charAt(0);
        }

        return null;
    }
}