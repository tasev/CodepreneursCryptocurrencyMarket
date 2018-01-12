package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.base;


import java.lang.ref.WeakReference;

import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.ErrorResponse;
import retrofit2.Response;

/**
 * Created by tasev on 12/8/17.
 */

public class BaseCallback {
    protected WeakReference<IBaseCallbackListener> weakReference;

    public BaseCallback(IBaseCallbackListener listener) {
        weakReference = new WeakReference<>(listener);
    }

    protected boolean responseIsOK(Response response) {
        IBaseCallbackListener listener = weakReference.get();
        if (listener == null) {
            return false;
        }
        if (!response.isSuccessful()) {
            ErrorResponse error = ErrorApiHandler.parseError(response);
            listener.handleCommonError(error);
            return false;
        }
        return true;
    }


    protected void onFailure(Throwable t) {
        IBaseCallbackListener listener = weakReference.get();
        if (listener == null) {
            return;
        }
        listener.handleNetworkFailure(t);
    }

}
