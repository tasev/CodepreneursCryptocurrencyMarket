package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils;

import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.model.CryptoData;

/**
 * Created by tasev on 12/8/17.
 */

public class UtilCryptoData {

    public static double getPriceInVal(CryptoData cryptoData, String val) {
        if (cryptoData == null)
            return 0;
        double price = cryptoData.price_usd;
        switch (val) {
            case UtilApiConstants.EUR:
                price = cryptoData.price_eur;
                break;
            case UtilApiConstants.CNY:
                price = cryptoData.price_cny;
                break;

        }
        return price;
    }

    public static double getHVolumeInVal(CryptoData cryptoData, String val) {
        if (cryptoData == null)
            return 0;
        double volume = cryptoData.h_volume_usd;
        switch (val) {
            case UtilApiConstants.EUR:
                volume = cryptoData.h_volume_eur;
                break;
            case UtilApiConstants.CNY:
                volume = cryptoData.h_volume_cny;
                break;
        }
        return volume;
    }

    public static double getMarketCapInVal(CryptoData cryptoData, String val) {
        if (cryptoData == null)
            return 0;
        double volume = cryptoData.market_cap_usd;
        switch (val) {
            case UtilApiConstants.EUR:
                volume = cryptoData.market_cap_eur;
                break;
            case UtilApiConstants.CNY:
                volume = cryptoData.market_cap_cny;
                break;
        }
        return volume;
    }
}
