package br.com.positionalfile.processor;

import br.com.positionalfile.RecordLayout;
import br.com.positionalfile.processor.reader.Delimiter;
import br.com.positionalfile.processor.reader.FieldPosition;
import br.com.positionalfile.processor.reader.Matcher;

import java.util.Objects;

@Delimiter(matcher = Matcher.SIZE_EQUALS_TO, value = "14")
public class HeaderWithSizeEqualDelimiter implements RecordLayout {

    public HeaderWithSizeEqualDelimiter() {}

    public HeaderWithSizeEqualDelimiter(final String title) {
        this.title = title;
    }

    @FieldPosition(begin = 0, end = 14)
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaderWithSizeEqualDelimiter header = (HeaderWithSizeEqualDelimiter) o;
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