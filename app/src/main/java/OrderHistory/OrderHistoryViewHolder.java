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

    public TextView orderNo, orderName, orderPrice, orderAddress, orderPhone;
    private Context context;

    public OrderHistoryViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        orderNo = (TextView) itemView.findViewById(R.id.order_history_no);
        orderName = (TextView) itemView.findViewById(R.id.order_history_name);
        orderAddress = (TextView) itemView.findViewById(R.id.order_history_address);
        orderPhone = (TextView) itemView.findViewById(R.id.order_history_phone);
        orderPrice = (TextView) itemView.findViewById(R.id.order_history_price);

    }
}
