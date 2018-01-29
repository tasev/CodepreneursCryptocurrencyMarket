package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.activities.ActivitySettings;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.activities.CryptoCurrencyDetails;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.adapters.CryptoRecyclerAdapter;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.ApiRequestFunctions;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.cryptodatas.GetCryptoDatasCallback;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.cryptodatas.GetCryptoDatasListener;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.base.BaseActivity;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.listeners.CryptoAdapterInteractionsListener;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.model.CryptoData;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils.UtilApiConstants;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils.UtilSettings;
import retrofit2.Callback;

import static market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils.CommonStringUtils.getNonNullString;

public class MainActivity extends BaseActivity implements CryptoAdapterInteractionsListener, GetCryptoDatasListener, SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {

    private static String TAG = "MainActivity";
    private List<CryptoData> cryptoDataList = new ArrayList<>();
    private CryptoRecyclerAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefresh;

    private String lastConvertValApiCall = "";
    private int lastLimitApiCall = 0;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiseActivity();
        initialiseAdapter();
        initialiseRecyclerView();
        getCryptoDatasFromApi(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.MAIN");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    //extract our message from intent
                    String msg_for_me = intent.getStringExtra("notification");
                    String notificationBody = intent.getStringExtra("notificationBody");
                    //log our message value
                    if (msg_for_me != null && !msg_for_me.equals(""))
                        handleFirebaseNotification(msg_for_me, notificationBody);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        //registering our receiver
        registerReceiver(mReceiver, intentFilter);
    }

    public void handleFirebaseNotification(String notification, String notificationBody) {
        Toast.makeText(this, notification + " -//- " + notificationBody, Toast.LENGTH_SHORT).show();
    }

    public void getCryptoDatasIfSettingsChanged(String convertVal, int limit, Callback<List<CryptoData>> callback) {
        if (lastConvertValApiCall.equals(convertVal) && lastLimitApiCall == limit)
            return;
        getCryptoDatas(convertVal, limit, callback);
    }

    public void getCryptoDatas(String convertVal, int limit, Callback<List<CryptoData>> callback) {
        lastConvertValApiCall = convertVal;
        lastLimitApiCall = limit;
        showProgress();
        ApiRequestFunctions.getCryptoDatas(convertVal, limit, callback);
    }

    @Override
    public void rowClicked(CryptoData cryptoData) {
        rowCryptoDataClicked(cryptoData);
    }

    public void rowCryptoDataClicked(CryptoData cryptoData) {
        openCryptoDataDetails(cryptoData);
    }

    @Override
    public void getCryptoDatasSuccessful(List<CryptoData> getCryptoDatasResponse) {
        if (!isVisible)
            return;
        hideProgress();
        swipeRefresh.setRefreshing(false);
        prepareCryptoDataData(getCryptoDatasResponse);
    }

    @Override
    public void getCryptoDatasUnsuccessful(Throwable t) {
        if (!isVisible)
            return;
        hideProgress();
        swipeRefresh.setRefreshing(false);
        Log.d(TAG, "getCryptoDatasUnsuccessful");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager != null ? searchManager.getSearchableInfo(MainActivity.this.getComponentName()) : null);
            searchView.setOnQueryTextListener(this);
        }

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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filter(newText, cryptoDataList);
        return false;
    }

    public void filter(String text, List<CryptoData> cryptoDataList) {
        if (getNonNullString(text).equals("")) {
            updateFiteredAdapterItems(cryptoDataList);
            return;
        }
        List<CryptoData> temp = new ArrayList();
        for (CryptoData d : cryptoDataList) {
            if (d != null && ((d.name.toLowerCase()).contains(text.toLowerCase()) || (d.symbol.toLowerCase()).contains(text.toLowerCase()))) {
                temp.add(d);
            }
        }
        updateFiteredAdapterItems(temp);
    }

    @Override
    public void onRefresh() {
        getCryptoDatasFromApi(false);
    }

    public void updateFiteredAdapterItems(List<CryptoData> filteredCryptoDataList) {
        mAdapter.changeList(filteredCryptoDataList);
    }

    public void openCryptoDataDetails(CryptoData cryptoData) {
        startActivity(new Intent(MainActivity.this, CryptoCurrencyDetails.class).putExtra(UtilApiConstants.CRYPTO_DATA_EXTRA_ID, cryptoData.id));
    }

    public void openSettingsActivity() {
        startActivity(new Intent(MainActivity.this, ActivitySettings.class));
    }

    private void prepareCryptoDataData(List<CryptoData> getCountriesResponse) {
        cryptoDataList = getCountriesResponse;
        mAdapter.currentconvertVal = UtilSettings.getInstance().getCurrentconvertVal();
        mAdapter.changeList(getCountriesResponse);
    }

    private void getCryptoDatasFromApi(boolean showProgress) {
        if (showProgress)
            showProgress();
        getCryptoDatas(UtilSettings.getInstance().getCurrentconvertVal(), UtilSettings.getInstance().getCurrentconvertLimit(), new GetCryptoDatasCallback(MainActivity.this));
    }

    private void initialiseActivity() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
        swipeRefresh.setOnRefreshListener(this);
    }

    private void initialiseAdapter() {
        mAdapter = new CryptoRecyclerAdapter(cryptoDataList, this);
    }

    private void initialiseRecyclerView() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
    }

}
