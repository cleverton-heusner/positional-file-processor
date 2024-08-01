package br.com.positionalfile.processor.reader;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FieldPosition {

    int begin();
    int end();
}
