package br.com.positionalfile.formatter.text;

import br.com.positionalfile.formatter.Alignment;
import br.com.positionalfile.formatter.Formatter;

import java.lang.annotation.Annotation;

public class TextFormatter implements Formatter {

    private final Annotation params;

    public TextFormatter(final Annotation params) {
        this.params = params;
    }

    public String format(final String unformattedText) {
        final Text textParams = (Text) params;

        return alignText(unformattedText, textParams.maxSize(), textParams.paddingChar(), textParams.alignment());
    }

    public String alignText(final String originalText, final int maxSize, final char paddingChar, final Alignment alignment) {
        if (originalText.length() >= maxSize) {
            return originalText.substring(0, maxSize);
        }

        final char[] alignedText = new char[maxSize];
        final int totalCharsToAdd = maxSize - originalText.length();

        if (alignment.equals(Alignment.RIGHT)) {
            for (int i = 0; i < maxSize; i++) {
                alignedText[i] = (i < totalCharsToAdd) ? paddingChar : originalText.charAt(i - totalCharsToAdd);
            }
        } else {
            for (int i = 0; i < maxSize; i++) {
                alignedText[i] = (i < originalText.length()) ? originalText.charAt(i) : paddingChar;
            }
        }

        return new String(alignedText);
    }
}