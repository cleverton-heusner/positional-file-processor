package br.com.positionalfile.formatter;

import br.com.positionalfile.formatter.text.Text;
import br.com.positionalfile.formatter.text.TextFormatter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class TextFormatterTests {

    private static final char CUSTOM_PADDING_CHAR = '_';

    @Text(maxSize = 20)
    private static class TextFieldWithLeadingSpaces {}

    @Text(maxSize = 20, alignment = Alignment.LEFT)
    private static class TextFieldWithTrailingSpaces {}

    @Text(maxSize = 20, paddingChar = CUSTOM_PADDING_CHAR)
    private static class TextFieldWithLeadingCustomPaddingChars {}

    @Text(maxSize = 20, alignment = Alignment.LEFT, paddingChar = CUSTOM_PADDING_CHAR)
    private static class TextFieldWithTrailingCustomPaddingChars {}

    @Test
    public void when_textWithLargerMaxSize_then_textWithLeadingSpaces() {
        // Arrange
        final String expectedFormattedText = "     myAccountNumber";
        Text textAnnotation = getTextAnnotationFromField(TextFieldWithLeadingSpaces.class);
        final TextFormatter textFormatter = new TextFormatter(textAnnotation);

        // Act
        final String actualFormattedText = textFormatter.format("myAccountNumber");

        // Assert
        Assertions.assertThat(actualFormattedText).isEqualTo(expectedFormattedText);
    }

    @Test
    public void when_textWithLargerMaxSize_then_textWithTrailingSpaces() {
        // Arrange
        final String expectedFormattedText = "myAccountNumber     ";
        Text textAnnotation = getTextAnnotationFromField(TextFieldWithTrailingSpaces.class);
        final TextFormatter textFormatter = new TextFormatter(textAnnotation);

        // Act
        final String actualFormattedText = textFormatter.format("myAccountNumber");

        // Assert
        Assertions.assertThat(actualFormattedText).isEqualTo(expectedFormattedText);
    }

    @Test
    public void when_textWithLargerMaxSize_then_textWithLeadingCustomPaddingChars() {
        // Arrange
        final String expectedFormattedText = "_____myAccountNumber";
        Text textAnnotation = getTextAnnotationFromField(TextFieldWithLeadingCustomPaddingChars.class);
        final TextFormatter textFormatter = new TextFormatter(textAnnotation);

        // Act
        final String actualFormattedText = textFormatter.format("myAccountNumber");

        // Assert
        Assertions.assertThat(actualFormattedText).isEqualTo(expectedFormattedText);
    }

    @Test
    public void when_textWithLargerMaxSize_then_textWithTrailingCustomPaddingChars() {
        // Arrange
        final String expectedFormattedText = "myAccountNumber_____";
        Text textAnnotation = getTextAnnotationFromField(TextFieldWithTrailingCustomPaddingChars.class);
        final TextFormatter textFormatter = new TextFormatter(textAnnotation);

        // Act
        final String actualFormattedText = textFormatter.format("myAccountNumber");

        // Assert
        Assertions.assertThat(actualFormattedText).isEqualTo(expectedFormattedText);
    }

    private Text getTextAnnotationFromField(final Class<?> decimalFieldClass) {
        return decimalFieldClass.getAnnotation(Text.class);
    }
}
