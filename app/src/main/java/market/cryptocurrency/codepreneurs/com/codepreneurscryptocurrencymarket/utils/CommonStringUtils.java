package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils;

/**
 * Created by tasev on 12/8/17.
 */

public class CommonStringUtils {

    public static String getNonNullString(String text) {
        return getNonNullString(text, "");
    }

    public static String getNonNullString(String text, String replacement) {
        return text != null ? text : replacement;
    }
}
