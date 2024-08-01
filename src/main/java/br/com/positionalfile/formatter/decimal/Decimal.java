package br.com.positionalfile.formatter.decimal;

import br.com.positionalfile.formatter.Alignment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static br.com.positionalfile.formatter.Alignment.RIGHT;

@Retention(RetentionPolicy.RUNTIME)
public @interface Decimal {

    boolean isSigned() default false;
    Alignment alignment() default RIGHT;
    char paddingChar() default ' ';
    int maxSize() default 0;
}