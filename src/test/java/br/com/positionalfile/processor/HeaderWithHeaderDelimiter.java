package br.com.positionalfile.processor;

import br.com.positionalfile.RecordLayout;
import br.com.positionalfile.processor.reader.Delimiter;
import br.com.positionalfile.processor.reader.FieldPosition;

import java.util.Objects;

@Delimiter("HEADER")
public class HeaderWithHeaderDelimiter implements RecordLayout {

    @FieldPosition(begin = 0, end = 6)
    private String title;

    public HeaderWithHeaderDelimiter(String title) {
        this.title = title;
    }

    public HeaderWithHeaderDelimiter() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaderWithHeaderDelimiter header = (HeaderWithHeaderDelimiter) o;
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