package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api;

import java.util.List;

import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.model.CryptoData;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by tasev on 12/8/17.
 */

public class ApiRequestFunctions {
    static RestApi api;

    static {
        api = new RestApi();
    }

    public static void getCryptoDatas(String convertVal, int limit, Callback<List<CryptoData>> callback) {
        Call<List<CryptoData>> call = api.getCryptoDatas(convertVal, limit);
        call.enqueue(callback);
    }

    public static void getCryptoCurrencyDetails(String cryptoId, String convertVal, Callback<List<CryptoData>> callback) {
        Call<List<CryptoData>> call = api.getCryptoCurrencyDetails(cryptoId, convertVal);
        call.enqueue(callback);
    }

}
