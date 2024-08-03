package br.com.positionalfile.processor;

import br.com.positionalfile.RecordLayout;
import br.com.positionalfile.processor.reader.Delimiter;
import br.com.positionalfile.processor.reader.FieldPosition;
import br.com.positionalfile.processor.reader.Matcher;

import java.util.Objects;

@Delimiter(value = "***", matcher = Matcher.START_WITH)
public class HeaderWithStarsDelimiter implements RecordLayout {

    public HeaderWithStarsDelimiter() {}

    public HeaderWithStarsDelimiter(final String title) {
        this.title = title;
    }

    @FieldPosition(begin = 0, end = 14)
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaderWithStarsDelimiter header = (HeaderWithStarsDelimiter) o;
        return Objects.equals(title, header.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}