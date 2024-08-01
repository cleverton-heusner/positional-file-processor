package br.com.positionalfile.formatter.decimal;

public enum Sign {
    PLUS('+'),
    MINUS('-');

    private final char sign;

    Sign(final char sign) {
        this.sign = sign;
    }

    public char get() {
        return sign;
    }
}
