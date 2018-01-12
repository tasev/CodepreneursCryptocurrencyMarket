package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.R;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.base.BaseActivity;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils.UtilSettings;

import static market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils.UtilApiConstants.CNY;
import static market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils.UtilApiConstants.EUR;

/**
 * Created by tasev on 12/9/17.
 */

public class ActivitySettings extends BaseActivity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.radiButtonUSD)
    RadioButton radiButtonUSD;

    @BindView(R.id.radiButtonEUR)
    RadioButton radiButtonEUR;

    @BindView(R.id.radiButtonCUD)
    RadioButton radiButtonCUD;

    int[] limitList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiseActivity();
        initiateViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.nav_done:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        UtilSettings.getInstance().setCurrentconvertLimit(limitList[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked)
            UtilSettings.getInstance().setCurrentconvertVal(buttonView.getText().toString());
    }

    private void initiateViews() {
        String currentConvertVal = UtilSettings.getInstance().getCurrentconvertVal();
        int currentconvertLimit = UtilSettings.getInstance().getCurrentconvertLimit();
        switch (currentConvertVal) {
            case EUR:
                radiButtonEUR.setChecked(true);
                break;
            case CNY:
                radiButtonCUD.setChecked(true);
                break;
            default:
                radiButtonUSD.setChecked(true);
        }
        limitList = getResources().getIntArray(R.array.limit_array_int);
        for (int i = 0; i < limitList.length; i++) {
            if (currentconvertLimit == limitList[i]) {
                spinner.setSelection(i);
                break;
            }
        }
        spinner.setOnItemSelectedListener(this);
        radiButtonCUD.setOnCheckedChangeListener(this);
        radiButtonEUR.setOnCheckedChangeListener(this);
        radiButtonUSD.setOnCheckedChangeListener(this);
    }

    private void initialiseActivity() {
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
    }


}
