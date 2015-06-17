package Cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import app.TheDreamStop.R;

/**
 * Created by srikrishna on 19-04-2015.
 */
public class CartCardViewHolder extends RecyclerView.ViewHolder{

    public TextView itemname, itemCost, itemQuantity, itemUnit, itemTotalCost, itemMultiplier;
    private Context context;

    public CartCardViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        itemname = (TextView) itemView.findViewById(R.id.cart_itemname);
        itemCost = (TextView) itemView.findViewById(R.id.cart_item_cost);
        itemQuantity = (TextView) itemView.findViewById(R.id.cart_item_quantity);
        itemUnit = (TextView) itemView.findViewById(R.id.cart_item_unit);
        itemTotalCost = (TextView) itemView.findViewById(R.id.cart_item_totprice);
        itemMultiplier = (TextView) itemView.findViewById(R.id.cart_item_multiplier);
    }}
