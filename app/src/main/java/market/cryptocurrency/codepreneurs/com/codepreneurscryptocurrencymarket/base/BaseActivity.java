package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.R;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.ErrorResponse;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.base.IBaseCallbackListener;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils.ProgressDialogHandler;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils.UtilUI;

/**
 * Created by tasev on 12/8/17.
 */

public class BaseActivity extends AppCompatActivity implements IBaseCallbackListener {

    protected boolean isVisible = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isVisible = false;
    }

    @Override
    public void handleNetworkFailure(Throwable t) {
        hideProgress();
    }

    @Override
    public void handleCommonError(ErrorResponse error) {
        hideProgress();
        if (error != null) {
            showAlertDialogWithOneButton("", error.message, null);
            return;
        }
        showAlertDialogWithOneButton("", getString(R.string.error_message), null);
    }

    public void showProgress() {
        showProgress(null);
    }

    public void showProgress(String title) {
        ProgressDialogHandler.getInstance().showProgressDialog(title, this);
    }

    public void hideProgress() {
        ProgressDialogHandler.getInstance().hideProgressDialog();
    }

    public void showAlertDialogWithOneButton(String title, String description, DialogInterface.OnClickListener onClickListener) {
        UtilUI.showAlertDialogWithOneButton(this, title, description, onClickListener);
    }

}
