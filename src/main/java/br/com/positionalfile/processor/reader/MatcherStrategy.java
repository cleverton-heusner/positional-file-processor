package br.com.positionalfile.processor.reader;

import java.util.function.BiFunction;

public class MatcherStrategy {

    public BiFunction<String, String, Boolean> select(final Matcher matcher) {
        if (Matcher.EQUALS.equals(matcher)) {
            return String::equals;
        }
        else if (Matcher.NOT_EQUALS.equals(matcher)) {
            return (value, delimiter) -> !value.equals(delimiter);
        }
        else if (Matcher.START_WITH.equals(matcher)) {
            return String::startsWith;
        }
        else if (Matcher.NOT_START_WITH.equals(matcher)) {
            return (value, delimiter) -> !value.startsWith(delimiter);
        }

        return null;
    }
}