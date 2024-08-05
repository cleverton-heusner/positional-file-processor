package br.com.positionalfile.processor.reader;

import br.com.positionalfile.RecordLayout;

public class DelimiterWrapper {
    private String value;
    private Matcher matcher;
    private Class<? extends RecordLayout> recordClass;

    public DelimiterWrapper(final Class<? extends RecordLayout> recordClass) {
        this.recordClass = recordClass;
        value = getDelimiterValue();
        matcher = getDelimiterMatcher();
    }

    public boolean hasEmptyValue() {
        return value.isEmpty();
    }

    public String getValue() {
        return value;
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public void setRecordClass(Class<? extends RecordLayout> recordClass) {
        this.recordClass = recordClass;
        value = getDelimiterValue();
        matcher = getDelimiterMatcher();
    }

    public Class<? extends RecordLayout> getRecordClass() {
        return recordClass;
    }

    private String getDelimiterValue() {
        return recordClass.getAnnotation(Delimiter.class).value();
    }

    private Matcher getDelimiterMatcher() {
        return recordClass.getAnnotation(Delimiter.class).matcher();
    }
}