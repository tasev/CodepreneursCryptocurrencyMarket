package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils;

/**
 * Created by tasev on 12/8/17.
 */

public class UtilSettings {

    private String currentconvertVal = UtilApiConstants.USD;
    private int currentconvertLimit = 100;

    private static UtilSettings instance;

    private UtilSettings() {
        //empty constructor
    }

    public static UtilSettings getInstance() {
        if (instance == null) {
            instance = new UtilSettings();
        }
        return instance;
    }

    public void setCurrentconvertVal(String currentconvertVal) {
        this.currentconvertVal = currentconvertVal;
    }

    public void setCurrentconvertLimit(int currentconvertLimit) {
        this.currentconvertLimit = currentconvertLimit;
    }

    public int getCurrentconvertLimit() {
        return currentconvertLimit;
    }

    public String getCurrentconvertVal() {
        return currentconvertVal;
    }

}
