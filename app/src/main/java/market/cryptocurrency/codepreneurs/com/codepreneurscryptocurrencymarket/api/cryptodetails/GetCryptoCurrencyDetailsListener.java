package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.cryptodetails;

import java.util.List;

import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.base.IBaseCallbackListener;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.model.CryptoData;


/**
 * Created by tasev on 12/8/17.
 */

public interface GetCryptoCurrencyDetailsListener extends IBaseCallbackListener {

    void getCryptoCurrencyDetailsSuccessful(List<CryptoData> getCryptoDataResponse);

    void getCryptoCurrencyDetailsUnsuccessful(Throwable t);
}
