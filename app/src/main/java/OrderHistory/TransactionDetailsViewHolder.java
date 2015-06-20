package OrderHistory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import app.TheDreamStop.R;

/**
 * Created by Suganprabu on 26-05-2015.
 */
public class TransactionDetailsViewHolder extends RecyclerView.ViewHolder{

    public TextView name, price, quantity, unit, total;
    private Context context;

    public TransactionDetailsViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        name = (TextView) itemView.findViewById(R.id.transaction_details_name);
        price = (TextView) itemView.findViewById(R.id.transaction_details_price);
        quantity = (TextView) itemView.findViewById(R.id.transaction_details_quantity);
        unit = (TextView) itemView.findViewById(R.id.transaction_details_unit);
        total = (TextView) itemView.findViewById(R.id.transaction_details_total);
    }
}
