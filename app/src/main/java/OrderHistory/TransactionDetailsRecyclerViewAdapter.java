package OrderHistory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.TheDreamStop.R;

/**
 * Created by Suganprabu on 26-05-2015.
 */
public class TransactionDetailsRecyclerViewAdapter extends RecyclerView.Adapter<TransactionDetailsViewHolder> {

    private ArrayList<TransactionDetailsClass> transactions;
    private Context context;

    public TransactionDetailsRecyclerViewAdapter(ArrayList<TransactionDetailsClass> items,Context context){
        this.transactions = items;
        this.context = context;
    }

    @Override
    public TransactionDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.transaction_card, parent, false);
        TransactionDetailsViewHolder vH = new TransactionDetailsViewHolder(context, v);

        return vH;

    }

    @Override
    public void onBindViewHolder(TransactionDetailsViewHolder holder, int position) {

        final TransactionDetailsClass transaction = transactions.get(position);

        holder.name.setText(transaction.getName());
        holder.price.setText(transaction.getPrice());
        holder.quantity.setText(transaction.getQuantity());
        holder.unit.setText(transaction.getUnit());
        holder.total.setText(transaction.getTotal());
    }

    @Override
    public int getItemCount() {
        return transactions == null ? 0 : transactions.size();
    }
}
