package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api;

import java.util.List;
import java.util.concurrent.TimeUnit;

import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.BuildConfig;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.model.CryptoData;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tasev on 12/8/17.
 */

public class RestApi {

    public static final int request_max_time_in_secconds = 20;

    public Retrofit getRetrofitInstance() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor())
                .readTimeout(request_max_time_in_secconds, TimeUnit.SECONDS)
                .connectTimeout(request_max_time_in_secconds, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public ApiService request() {
        return getRetrofitInstance().create(ApiService.class);
    }

    public Call<List<CryptoData>> getCryptoDatas(String convertVal, int limit) {
        return request().getCryptoDatas(convertVal, limit);
    }

    public Call<List<CryptoData>> getCryptoCurrencyDetails(String cryptoId, String convertVal) {
        return request().getCryptoCurrencyDetails(cryptoId, convertVal);
    }
}
