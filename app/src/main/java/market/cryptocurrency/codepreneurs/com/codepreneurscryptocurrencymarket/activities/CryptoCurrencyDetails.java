package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.R;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.ApiRequestFunctions;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.cryptodetails.GetCryptoCurrencyDetailsCallback;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.cryptodetails.GetCryptoCurrencyDetailsListener;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.base.BaseActivity;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.model.CryptoData;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils.CommonStringUtils;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils.UtilApiConstants;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils.UtilCryptoData;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils.UtilSettings;
import retrofit2.Callback;

/**
 * Created by tasev on 12/8/17.
 */

public class CryptoCurrencyDetails extends BaseActivity implements GetCryptoCurrencyDetailsListener, SwipeRefreshLayout.OnRefreshListener {

    private static String TAG = "CryptoCurrencyDetails";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.txtRank)
    TextView txtRank;

    @BindView(R.id.txtName)
    TextView txtName;

    @BindView(R.id.txtSymbol)
    TextView txtSymbol;

    @BindView(R.id.txtPrice)
    TextView txtPrice;

    @BindView(R.id.txtHVolume)
    TextView txtHVolume;

    @BindView(R.id.txtMarketCap)
    TextView txtMarketCap;

    @BindView(R.id.txtPriceInBitCoin)
    TextView txtPriceInBitCoin;

    @BindView(R.id.txtOneHChange)
    TextView txtOneHChange;

    @BindView(R.id.txttwentyFourHChange)
    TextView txttwentyFourHChange;

    @BindView(R.id.txtSevenDChange)
    TextView txtSevenDChange;

    @BindView(R.id.txtTotalSupply)
    TextView txtTotalSupply;

    @BindView(R.id.txtAvailableSupply)
    TextView txtAvailableSupply;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefresh;


    CryptoData cryptoData = new CryptoData();
    private String lastConvertValApiCall = "";
    private String lastcryptoIdApiCall = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiseActivity();
        if (cryptoData == null)
            return;
        getCurrencyDetailsFromApi(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideProgress();
        getCryptoDatasIfSettingsChanged(cryptoData.id, UtilSettings.getInstance().getCurrentconvertVal(), new GetCryptoCurrencyDetailsCallback(CryptoCurrencyDetails.this));
    }

    public void getCryptoCurrencyDetails(String cryptoId, String convertVal, Callback<List<CryptoData>> callback) {
        lastConvertValApiCall = convertVal;
        lastcryptoIdApiCall = cryptoId;
        ApiRequestFunctions.getCryptoCurrencyDetails(cryptoId, convertVal, callback);
    }

    public void getCryptoDatasIfSettingsChanged(String cryptoId, String convertVal, Callback<List<CryptoData>> callback) {
        if (lastConvertValApiCall.equals(convertVal) && lastcryptoIdApiCall.equals(cryptoId))
            return;
        showProgress();
        getCryptoCurrencyDetails(cryptoId, convertVal, callback);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.nav_settings:
                openSettingsActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void getCryptoCurrencyDetailsSuccessful(List<CryptoData> getCryptoDataResponse) {
        if (!isVisible)
            return;
        hideProgress();
        swipeRefresh.setRefreshing(false);
        if (getCryptoDataResponse != null && getCryptoDataResponse.size() > 0)
            cryptoData = getCryptoDataResponse.get(0);
        initialiseViews();
    }

    @Override
    public void getCryptoCurrencyDetailsUnsuccessful(Throwable t) {
        if (!isVisible)
            return;
        hideProgress();
        swipeRefresh.setRefreshing(false);
        Log.d(TAG, "getCryptoCurrencyDetailsUnsuccessful");
    }

    @Override
    public void onRefresh() {
        getCurrencyDetailsFromApi(false);
    }

    public void openSettingsActivity() {
        startActivity(new Intent(CryptoCurrencyDetails.this, ActivitySettings.class));
    }

    private void getCurrencyDetailsFromApi(boolean showProgress) {
        if (showProgress)
            showProgress();
        getCryptoCurrencyDetails(cryptoData.id, UtilSettings.getInstance().getCurrentconvertVal(), new GetCryptoCurrencyDetailsCallback(CryptoCurrencyDetails.this));
    }


    private void initialiseViews() {
        String currentconvertVal = UtilSettings.getInstance().getCurrentconvertVal();
        txtRank.setText(String.valueOf(cryptoData.rank));
        txtName.setText(CommonStringUtils.getNonNullString(cryptoData.name));
        txtSymbol.setText(cryptoData.symbol);
        txtPrice.setText(String.valueOf(UtilCryptoData.getPriceInVal(cryptoData, currentconvertVal)));
        txtHVolume.setText(String.valueOf(UtilCryptoData.getHVolumeInVal(cryptoData, currentconvertVal)));
        txtMarketCap.setText(String.valueOf(UtilCryptoData.getMarketCapInVal(cryptoData, currentconvertVal)));
        txtPriceInBitCoin.setText(String.valueOf(cryptoData.price_btc));
        txtOneHChange.setText(String.valueOf(cryptoData.percent_change_1h));
        txttwentyFourHChange.setText(String.valueOf(cryptoData.percent_change_24h));
        txtSevenDChange.setText(String.valueOf(cryptoData.percent_change_7d));
        txtTotalSupply.setText(String.valueOf(cryptoData.total_supply));
        txtAvailableSupply.setText(String.valueOf(cryptoData.available_supply));
        swipeRefresh.setOnRefreshListener(this);
    }

    private void initialiseActivity() {
        setContentView(R.layout.crypto_currency_details);
        cryptoData.id = getIntent().getStringExtra(UtilApiConstants.CRYPTO_DATA_EXTRA_ID);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
    }

}
