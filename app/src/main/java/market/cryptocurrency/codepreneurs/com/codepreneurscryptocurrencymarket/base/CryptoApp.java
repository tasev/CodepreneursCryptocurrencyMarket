package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.base;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Tasev on 3/22/2017.
 */

public class CryptoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }
}