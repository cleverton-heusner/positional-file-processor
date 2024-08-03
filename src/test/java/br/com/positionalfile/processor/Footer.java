package br.com.positionalfile.processor;

import br.com.positionalfile.RecordLayout;
import br.com.positionalfile.processor.reader.Delimiter;
import br.com.positionalfile.processor.reader.FieldPosition;

import java.util.Objects;

@Delimiter
public class Footer implements RecordLayout {

    public Footer() {}

    public Footer(final String title) {
        this.title = title;
    }

    @FieldPosition(begin = 0, end = 14)
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Footer footer = (Footer) o;
        return Objects.equals(title, footer.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}