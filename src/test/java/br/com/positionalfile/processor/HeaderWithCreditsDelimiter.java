package br.com.positionalfile.processor;

import br.com.positionalfile.RecordLayout;
import br.com.positionalfile.processor.reader.Delimiter;
import br.com.positionalfile.processor.reader.FieldPosition;

import java.util.Objects;

@Delimiter("CREDITOS")
public class HeaderWithCreditsDelimiter implements RecordLayout {

    public HeaderWithCreditsDelimiter() {}

    public HeaderWithCreditsDelimiter(final String title) {
        this.title = title;
    }

    @FieldPosition(begin = 0, end = 8)
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaderWithCreditsDelimiter header = (HeaderWithCreditsDelimiter) o;
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