package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tasev on 12/8/17.
 */

public class CryptoData implements Serializable {

    public String id;
    public String name;
    public String symbol;
    public int rank;
    public double price_usd;
    public double price_btc;
    @SerializedName("24h_volume_usd")
    public double h_volume_usd;
    public double market_cap_usd;
    public double available_supply;
    public double total_supply;
    public double max_supply;
    public double percent_change_1h;
    public double percent_change_24h;
    public double percent_change_7d;
    public String last_updated;

    //EUR
    public double price_eur;
    @SerializedName("24h_volume_eur")
    public double h_volume_eur;
    public double market_cap_eur;

    //CNY
    public double price_cny;
    @SerializedName("24h_volume_cny")
    public double h_volume_cny;
    public double market_cap_cny;
}
