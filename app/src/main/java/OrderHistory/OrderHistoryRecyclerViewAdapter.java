package OrderHistory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.TheDreamStop.R;

/**
 * Created by Suganprabu on 22-05-2015.
 */
public class OrderHistoryRecyclerViewAdapter extends RecyclerView.Adapter<OrderHistoryViewHolder> {

    private ArrayList<OrderHistoryClass> orders;
    private Context context;

    public OrderHistoryRecyclerViewAdapter(ArrayList<OrderHistoryClass> items,Context context){
        this.orders = items;
        this.context = context;
    }

    @Override
    public OrderHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.order_history_card, parent, false);
        OrderHistoryViewHolder vH = new OrderHistoryViewHolder(context, v);

        return vH;
    }

    @Override
    public void onBindViewHolder(OrderHistoryViewHolder holder, int position) {

        OrderHistoryClass order = orders.get(position);

        holder.orderNo.setText(order.getOrderHistoryId());
        holder.orderDeliveryDate.setText(order.getOrderHistoryDate());
        holder.orderPrice.setText(order.getOrderHistoryPrice());
        holder.orderAddress.setText(order.getOrderHistoryAddress());

    }

    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }
}
