package br.com.positionalfile.processor.reader;

import java.util.function.BiFunction;

public class DelimiterMatcherStrategy {

    public BiFunction<String, String, Boolean> select(final Matcher delimiterMatcher) {
        if (Matcher.EQUALS_TO.equals(delimiterMatcher)) {
            return String::equals;
        }
        else if (Matcher.NOT_EQUALS_TO.equals(delimiterMatcher)) {
            return (value, delimiter) -> !value.equals(delimiter);
        }
        else if (Matcher.START_WITH.equals(delimiterMatcher)) {
            return String::startsWith;
        }
        else if (Matcher.NOT_START_WITH.equals(delimiterMatcher)) {
            return (value, delimiter) -> !value.startsWith(delimiter);
        }
        else if (Matcher.ENDS_WITH.equals(delimiterMatcher)) {
            return String::endsWith;
        }
        else if (Matcher.NOT_ENDS_WITH.equals(delimiterMatcher)) {
            return (value, delimiter) -> !value.endsWith(delimiter);
        }
        else if (Matcher.SIZE_EQUALS_TO.equals(delimiterMatcher)) {
            return (value, delimiterSize) -> value.length() == Integer.parseInt(delimiterSize);
        }
        else if (Matcher.SIZE_BIGGER_THAN.equals(delimiterMatcher)) {
            return (value, delimiterSize) -> value.length() > Integer.parseInt(delimiterSize);
        }
        else if (Matcher.SIZE_SMALLER_THAN.equals(delimiterMatcher)) {
            return (value, delimiterSize) -> value.length() < Integer.parseInt(delimiterSize);
        }
        else if (Matcher.CONTAINS.equals(delimiterMatcher)) {
            return String::contains;
        }

        return null;
    }
}