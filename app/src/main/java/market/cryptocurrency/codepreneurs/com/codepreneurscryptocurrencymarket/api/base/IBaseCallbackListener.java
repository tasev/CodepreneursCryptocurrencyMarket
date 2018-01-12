package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.base;


import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.ErrorResponse;

/**
 * Created by tasev on 12/8/17.
 */

public interface IBaseCallbackListener {

    void handleNetworkFailure(Throwable t);

    void handleCommonError(ErrorResponse error);
}
