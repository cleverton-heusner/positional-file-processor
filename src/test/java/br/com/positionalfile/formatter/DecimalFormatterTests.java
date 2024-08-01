package br.com.positionalfile.formatter;

import br.com.positionalfile.formatter.decimal.Decimal;
import br.com.positionalfile.formatter.decimal.DecimalFormatter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DecimalFormatterTests {

    private static final char CUSTOM_PADDING_CHAR = '0';

    @Decimal(isSigned = true)
    private static class SignedDecimalField {}

    @Decimal
    private static class UnsignedDecimalField {}

    @Decimal(maxSize = 20)
    private static class DecimalFieldWithLeadingSpaces {}

    @Decimal(maxSize = 20, paddingChar = CUSTOM_PADDING_CHAR)
    private static class DecimalFieldWithLeadingCustomPaddingChars {}

    @Decimal(maxSize = 20, alignment = Alignment.LEFT)
    private static class DecimalFieldWithTrailingSpaces {}

    @Decimal(isSigned = true, maxSize = 20, paddingChar = CUSTOM_PADDING_CHAR)
    private static class SignedDecimalFieldWithLeadingCustomPaddingChars {};

    @Decimal(isSigned = true, maxSize = 20)
    private static class SignedDecimalFieldWithLeadingSpaces {};

    @Test
    public void when_positiveDecimal_and_signParameterAsTrue_then_decimalWithSign() {
        // Arrange
        final String expectedFormattedDecimal = "+1000.00";
        Decimal decimalAnnotation = getDecimalAnnotationFromField(SignedDecimalField.class);
        final DecimalFormatter decimalFormatter = new DecimalFormatter(decimalAnnotation);

        // Act
        final String actualFormattedDecimal = decimalFormatter.format("1000.00");

        // Assert
        Assertions.assertThat(actualFormattedDecimal).isEqualTo(expectedFormattedDecimal);
    }

    @Test
    public void when_negativeDecimal_and_signParameterAsTrue_then_decimalWithSign() {
        // Arrange
        final String expectedFormattedDecimal = "-1000.00";
        Decimal decimalAnnotation = getDecimalAnnotationFromField(SignedDecimalField.class);
        final DecimalFormatter decimalFormatter = new DecimalFormatter(decimalAnnotation);

        // Act
        final String actualFormattedDecimal = decimalFormatter.format("-1000.00");

        // Assert
        Assertions.assertThat(actualFormattedDecimal).isEqualTo(expectedFormattedDecimal);
    }

    @Test
    public void when_positiveDecimal_and_signParameterAsFalse_then_decimalWithoutSign() {
        // Arrange
        final String expectedFormattedDecimal = "1000.00";
        Decimal decimalAnnotation = getDecimalAnnotationFromField(UnsignedDecimalField.class);
        final DecimalFormatter decimalFormatter = new DecimalFormatter(decimalAnnotation);

        // Act
        final String actualFormattedDecimal = decimalFormatter.format("1000.00");

        // Assert
        Assertions.assertThat(actualFormattedDecimal).isEqualTo(expectedFormattedDecimal);
    }

    @Test
    public void when_negativeDecimal_and_signParameterAsFalse_then_decimalWithoutSign() {
        // Arrange
        final String expectedFormattedDecimal = "1000.00";
        Decimal decimalAnnotation = getDecimalAnnotationFromField(UnsignedDecimalField.class);
        final DecimalFormatter decimalFormatter = new DecimalFormatter(decimalAnnotation);

        // Act
        final String actualFormattedDecimal = decimalFormatter.format("-1000.00");

        // Assert
        Assertions.assertThat(actualFormattedDecimal).isEqualTo(expectedFormattedDecimal);
    }

    @Test
    public void when_maxSizeParameter_then_decimalAlignedToRightWithLeadingSpaces() {
        // Arrange
        final String expectedFormattedDecimal = "              1000.00";
        Decimal decimalAnnotation = getDecimalAnnotationFromField(DecimalFieldWithLeadingSpaces.class);
        final DecimalFormatter decimalFormatter = new DecimalFormatter(decimalAnnotation);

        // Act
        final String actualFormattedDecimal = decimalFormatter.format("1000.00");

        // Assert
        Assertions.assertThat(actualFormattedDecimal).isEqualTo(expectedFormattedDecimal);
    }

    @Test
    public void when_maxSizeParameter_then_decimalAlignedToRightWithLeadingCustomPaddingChars() {
        // Arrange
        final String expectedFormattedDecimal = "000000000000001000.00";
        Decimal decimalAnnotation = getDecimalAnnotationFromField(DecimalFieldWithLeadingCustomPaddingChars.class);
        final DecimalFormatter decimalFormatter = new DecimalFormatter(decimalAnnotation);

        // Act
        final String actualFormattedDecimal = decimalFormatter.format("1000.00");

        // Assert
        Assertions.assertThat(actualFormattedDecimal).isEqualTo(expectedFormattedDecimal);
    }

    @Test
    public void when_maxSizeParameter_then_decimalAlignedToLeftWithTrailingSpaces() {
        // Arrange
        final String expectedFormattedDecimal = "1000.00              ";
        Decimal decimalAnnotation = getDecimalAnnotationFromField(DecimalFieldWithTrailingSpaces.class);
        final DecimalFormatter decimalFormatter = new DecimalFormatter(decimalAnnotation);

        // Act
        final String actualFormattedDecimal = decimalFormatter.format("1000.00");

        // Assert
        Assertions.assertThat(actualFormattedDecimal).isEqualTo(expectedFormattedDecimal);
    }

    @Test
    public void when_positiveDecimal_and_signParameterAsTrue_and_maxSizeParameter_then_decimalAlignedToRightWithLeadingCustomPaddingChars() {
        // Arrange
        final String expectedFormattedDecimal = "+000000000000001000.00";
        Decimal decimalAnnotation = getDecimalAnnotationFromField(SignedDecimalFieldWithLeadingCustomPaddingChars.class);
        final DecimalFormatter decimalFormatter = new DecimalFormatter(decimalAnnotation);

        // Act
        final String actualFormattedDecimal = decimalFormatter.format("1000.00");

        // Assert
        Assertions.assertThat(actualFormattedDecimal).isEqualTo(expectedFormattedDecimal);
    }

    @Test
    public void when_positiveDecimal_and_signParameterAsTrue_and_maxSizeParameter_then_decimalAlignedToRightWithLeadingSpaces() {
        // Arrange
        final String expectedFormattedDecimal = "              +1000.00";
        Decimal decimalAnnotation = getDecimalAnnotationFromField(SignedDecimalFieldWithLeadingSpaces.class);
        final DecimalFormatter decimalFormatter = new DecimalFormatter(decimalAnnotation);

        // Act
        final String actualFormattedDecimal = decimalFormatter.format("1000.00");

        // Assert
        Assertions.assertThat(actualFormattedDecimal).isEqualTo(expectedFormattedDecimal);
    }

    private Decimal getDecimalAnnotationFromField(final Class<?> decimalFieldClass) {
        return decimalFieldClass.getAnnotation(Decimal.class);
    }
}
