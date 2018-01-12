package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tasev on 12/8/17.
 */

public class ErrorResponse {

    public List<String> errorCodes = new ArrayList<>();
    public String message;
    private ErrorDataObject data;

    public static ErrorResponse getDefaultErrorResponse() {
        ErrorResponse response = new ErrorResponse();
        response.message = "Unexpected error...";
        return response;
    }

    private class ErrorDataObject {
        public String dialogType;
    }

    public String getDialogType() {
        if (data != null && data.dialogType != null)
            return data.dialogType;

        return "";
    }
}
