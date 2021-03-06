package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.cryptodetails;


import java.util.List;

import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.base.BaseCallback;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.base.IBaseCallbackListener;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.model.CryptoData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tasev on 12/8/17.
 */

public class GetCryptoCurrencyDetailsCallback extends BaseCallback implements Callback<List<CryptoData>> {

    public GetCryptoCurrencyDetailsCallback(GetCryptoCurrencyDetailsListener listener) {
        super(listener);
    }

    @Override
    public void onResponse(Call<List<CryptoData>> call, Response<List<CryptoData>> response) {
        IBaseCallbackListener listener = weakReference.get();
        if (listener == null) return;
        if (!responseIsOK(response)) {
            return;
        }
        ((GetCryptoCurrencyDetailsListener) listener).getCryptoCurrencyDetailsSuccessful(response.body());
    }


    @Override
    public void onFailure(Call<List<CryptoData>> call, Throwable t) {
        IBaseCallbackListener listener = weakReference.get();
        if (listener == null) return;
        ((GetCryptoCurrencyDetailsListener) listener).getCryptoCurrencyDetailsUnsuccessful(t);
        onFailure(t);
    }
}
