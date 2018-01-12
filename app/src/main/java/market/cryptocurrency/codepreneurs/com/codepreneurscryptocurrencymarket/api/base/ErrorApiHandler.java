package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.base;

import java.io.IOException;
import java.lang.annotation.Annotation;

import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.ErrorResponse;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api.RestApi;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by tasev on 12/8/17.
 */

public class ErrorApiHandler {

    public static ErrorResponse parseError(Response<?> response) {
        Converter<ResponseBody, ErrorResponse> converter =
                (new RestApi()).getRetrofitInstance().responseBodyConverter(ErrorResponse.class, new Annotation[0]);
        ErrorResponse error;
        try {
            error = converter.convert(response.errorBody());
            return error;
        } catch (IOException | IllegalStateException e) {
            return ErrorResponse.getDefaultErrorResponse();
        }
    }

}
