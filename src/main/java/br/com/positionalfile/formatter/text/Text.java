package br.com.positionalfile.formatter.text;

import br.com.positionalfile.formatter.Alignment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static br.com.positionalfile.formatter.Alignment.RIGHT;

@Retention(RetentionPolicy.RUNTIME)
public @interface Text {

    Alignment alignment() default RIGHT;
    char paddingChar() default ' ';
    int maxSize() default 0;
}