package br.com.positionalfile.formatter;

import br.com.positionalfile.formatter.decimal.Decimal;
import br.com.positionalfile.formatter.decimal.DecimalFormatter;
import br.com.positionalfile.formatter.text.Text;
import br.com.positionalfile.formatter.text.TextFormatter;

import java.lang.annotation.Annotation;

public class FormatterStrategy {

    public Formatter select(final Annotation params) {
        final Class<? extends Annotation> annotationType = params.annotationType();

        if (Text.class.equals(annotationType)) {
            return new TextFormatter(params);
        }
        else if (Decimal.class.equals(annotationType)) {
            return new DecimalFormatter(params);
        }

        return null;
    }
}