package OrderHistory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import app.TheDreamStop.R;

/**
 * Created by Suganprabu on 22-05-2015.
 */
public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {

    public TextView orderNo, orderDeliveryDate, orderPrice, orderAddress;
    private Context context;

    public OrderHistoryViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        orderNo = (TextView) itemView.findViewById(R.id.order_history_no);
        orderDeliveryDate = (TextView) itemView.findViewById(R.id.order_history_date);
        orderAddress = (TextView) itemView.findViewById(R.id.order_history_address);
        orderPrice = (TextView) itemView.findViewById(R.id.order_history_price);

    }
}
