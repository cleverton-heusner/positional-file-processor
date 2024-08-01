package br.com.positionalfile.processor;

import br.com.positionalfile.Record;
import br.com.positionalfile.processor.reader.FieldPosition;
import br.com.positionalfile.processor.reader.Size;

import java.util.Objects;

@Size(1)
public class Footer implements Record {

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