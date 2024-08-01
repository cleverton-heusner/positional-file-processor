package br.com.positionalfile.formatter.decimal;

import br.com.positionalfile.formatter.Alignment;
import br.com.positionalfile.formatter.Formatter;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;

public class DecimalFormatter implements Formatter {

    private final Annotation params;

    public DecimalFormatter(final Annotation params) {
        this.params = params;
    }

    public String format(final String unformattedDecimal) {
        final Decimal decimalParams = (Decimal) params;
        final BigDecimal decimalNumber = new BigDecimal(unformattedDecimal);
        String formattedDecimal = decimalNumber.abs().toPlainString();

        formattedDecimal = alignDecimal(
                formattedDecimal,
                decimalParams.maxSize(),
                decimalParams.paddingChar(),
                decimalParams.alignment()
        );

        return addSignToDecimal(decimalParams, decimalNumber, formattedDecimal);
    }

    private String alignDecimal(final String notAlignedDecimal, final int maxSize, final char paddingChar, final Alignment alignment) {
        if (maxSize == 0) {
            return notAlignedDecimal;
        }

        final int totalCharsToAdd = Math.max(maxSize - countDigitsIn(notAlignedDecimal), 0);
        final var alignedDecimal = new StringBuilder(maxSize + notAlignedDecimal.length());

        if (Alignment.RIGHT.equals(alignment)) {
            for (int i = 0; i < totalCharsToAdd; i++) {
                alignedDecimal.append(paddingChar);
            }
            alignedDecimal.append(notAlignedDecimal);
        } else {
            alignedDecimal.append(notAlignedDecimal);
            for (int i = 0; i < totalCharsToAdd; i++) {
                alignedDecimal.append(paddingChar);
            }
        }

        return alignedDecimal.toString();
    }

    private int countDigitsIn(final String decimal) {
        int digitsCounter = 0;
        for (int i = 0; i < decimal.length(); i++) {
            if (Character.isDigit(decimal.charAt(i))) {
                digitsCounter++;
            }
        }

        return digitsCounter;
    }

    private String addSignToDecimal(final Decimal decimalParams, final BigDecimal decimalNumber, final String formattedDecimal) {
        if (decimalParams.isSigned()) {
            final char sign = decimalNumber.signum() >= 0 ? Sign.PLUS.get() : Sign.MINUS.get();
            for (int i = 0; i < formattedDecimal.length(); i++) {
                if (!Character.isWhitespace(formattedDecimal.charAt(i))) {
                    final StringBuilder formattedDecimalTemp =  new StringBuilder(formattedDecimal);
                    return formattedDecimalTemp.insert(i, sign).toString();
                }
            }
        }

        return formattedDecimal;
    }
}