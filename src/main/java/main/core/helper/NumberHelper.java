package main.core.helper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

/**
 * @author Sam
 */
public final class NumberHelper {

    private NumberHelper(){
        //Utility class constructor cannot be called.
    }

    /**
     * Parse a String to a BigDecimal.
     *
     * @param value String to parse.
     * @return BigDecimal retrieved from the given String.
     */
    public static BigDecimal parseToBigDecimal(String value) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(new Locale("nl", "NL"));
        formatter.setParseBigDecimal(true);
        return (BigDecimal) formatter.parse(value, new ParsePosition(0));
    }

    /**
     * Parse a BigDecimal to a String.
     *
     * @param value BigDecimal to parse.
     * @return String retrieved from the given BigDecimal.
     */
    public static String parseToString(BigDecimal value) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("nl", "NL"));
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setCurrencySymbol("");

        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        formatter.setDecimalFormatSymbols(symbols);

        return formatter.format(value.doubleValue()).substring(1);
    }
}
