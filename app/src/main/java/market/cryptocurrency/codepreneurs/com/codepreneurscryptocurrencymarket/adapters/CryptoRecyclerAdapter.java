package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.R;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.listeners.CryptoAdapterInteractionsListener;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.model.CryptoData;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils.UtilApiConstants;
import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.utils.UtilCryptoData;

/**
 * Created by tasev on 12/8/17.
 */

public class CryptoRecyclerAdapter extends RecyclerView.Adapter<CryptoRecyclerAdapter.MyViewHolder> {

    private List<CryptoData> cryptoDataList;
    private WeakReference<CryptoAdapterInteractionsListener> cryptoAdapterInteractionsListener;
    public String currentconvertVal = UtilApiConstants.USD;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtRank)
        TextView txtRank;

        @BindView(R.id.txtSymbol)
        TextView txtSymbol;

        @BindView(R.id.txtPrice)
        TextView txtPrice;

        @BindView(R.id.txtHVolume)
        TextView txtHVolume;

        @BindView(R.id.mainLayout)
        RelativeLayout mainLayout;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public CryptoRecyclerAdapter(List<CryptoData> cryptoDataList, CryptoAdapterInteractionsListener cryptoAdapterInteractionsListener) {
        this.cryptoDataList = cryptoDataList;
        this.cryptoAdapterInteractionsListener = new WeakReference<>(cryptoAdapterInteractionsListener);
    }

    public void changeList(List<CryptoData> cryptoDataList) {
        this.cryptoDataList = cryptoDataList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crypto_recyclerview_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CryptoData cryptoData = cryptoDataList.get(position);
        holder.txtRank.setText(String.valueOf(cryptoData.rank));
        holder.txtSymbol.setText(cryptoData.symbol);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CryptoAdapterInteractionsListener intListener = cryptoAdapterInteractionsListener.get();
                if (intListener == null) return;
                intListener.rowClicked(cryptoData);
            }
        });
        holder.txtPrice.setText(String.valueOf(UtilCryptoData.getPriceInVal(cryptoData, currentconvertVal)));
        holder.txtHVolume.setText(String.valueOf(UtilCryptoData.getHVolumeInVal(cryptoData, currentconvertVal)));

    }

    @Override
    public int getItemCount() {
        return cryptoDataList.size();
    }
}
