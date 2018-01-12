package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.R;

/**
 * Created by tasev on 12/8/17.
 */

public class ProgressDialogHandler {

    private boolean dialogIsVisible = false;

    private ProgressDialog dialog;

    private static ProgressDialogHandler instance;
    private ProgressDialogHandler(){}

    public static  ProgressDialogHandler getInstance() {
        if (instance == null) {
            instance = new ProgressDialogHandler();
        }
        return instance;
    }

    public synchronized void showProgressDialog(String title, Context context) {
        if (dialogIsVisible) return;
        try {
            dialogIsVisible = true;
            this.dialog = new ProgressDialog(context);
            this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.dialog.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
            this.dialog.getWindow().requestFeature(Window.FEATURE_PROGRESS);
            this.dialog.getWindow().requestFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
            this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            this.dialog.setCancelable(false);

            this.dialog.show();
            this.dialog.setContentView(R.layout.progress);
            TextView tv = (TextView) dialog.findViewById(R.id.progressTitle);
            if (title != null) {
                tv.setText(title);
            }

        } catch (Exception e) {
        }
    }

    public synchronized void hideProgressDialog() {
        if (dialog != null ) {
            dialog.dismiss();
        }
        dialogIsVisible = false;
    }




}
