package br.com.positionalfile;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Testes {

    public static void main(String[] args) {
//        String input = "example";
//        String regex = "^e+";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(input);
//        System.out.println(matcher.find());

        // MONETARIO
        // input: BIGDECIMAL
        // zeros/espacos/whatever a esquerda
        // separador de milhar
        BigDecimal number1 = new BigDecimal("-1234567.89");
        int totalLength1 = 18; // Comprimento total desejado, incluindo parte decimal e separadores
        String formattedNumber2 = padNumberWithFormatting(number1, totalLength1);
        System.out.println("Formatted Number: " + formattedNumber2);

        // separador decimal
    }

    private static String padNumberWithFormatting(BigDecimal number, int totalLength) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        if (number.signum() < 0) {
            number = number.negate();
        }

        numberFormat.setGroupingUsed(true);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        String formattedNumber = numberFormat.format(number);

        StringBuilder result = new StringBuilder(formattedNumber);
        return result.toString();
    }
}
