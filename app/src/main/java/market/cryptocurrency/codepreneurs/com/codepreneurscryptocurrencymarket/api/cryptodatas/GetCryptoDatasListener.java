package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.cryptodatas;

import java.util.List;

import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.base.IBaseCallbackListener;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.model.CryptoData;


/**
 * Created by tasev on 12/8/17.
 */

public interface GetCryptoDatasListener extends IBaseCallbackListener {

    void getCryptoDatasSuccessful(List<CryptoData> getCryptoDatasResponse);

    void getCryptoDatasUnsuccessful(Throwable t);
}
