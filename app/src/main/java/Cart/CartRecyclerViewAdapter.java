package Cart;

import android.content.Context;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import app.TheDreamStop.Master;
import app.TheDreamStop.R;


/**
 * Created by srikrishna on 19-04-2015.
 */
public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartCardViewHolder> {


    public static ArrayList<CartItemsClass> listitems;
    private Context context;
    //private ImageButton removeFromCart;
    //private ImageButton editCartItem;

    public CartRecyclerViewAdapter(ArrayList<CartItemsClass> items,Context context){
        this.listitems = items;
        this.context = context;
    }

    public CartCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_card, parent, false);
        CartCardViewHolder vH = new CartCardViewHolder(context, v);

        //removeFromCart = (ImageButton) v.findViewById(R.id.cart_removebutton);
        //editCartItem = (ImageButton) v.findViewById(R.id.cart_editbutton);

        return vH;
    }
    @Override
    public void onBindViewHolder(final CartCardViewHolder viewHolder,final int position) {
        final CartItemsClass item = listitems.get(position);

        final String name = item.getcartItemname();
        if(name.length()>10)
            viewHolder.itemname.setText(name.substring(0,10)+"...");
        else
            viewHolder.itemname.setText(name);
        viewHolder.itemname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(context, name, Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER,0,0);
                t.show();
            }
        });
        viewHolder.itemCost.setText(item.getCartitemprice());
        viewHolder.itemTotalCost.setText("Rs. " + String.valueOf(item.getQuantity()*Float.parseFloat(item.getCartitemprice())));
        viewHolder.itemQuantity.setText(String.valueOf(item.getQ()));
        viewHolder.itemMultiplier.setText("X " + String.valueOf(item.getQuantity()));
        viewHolder.itemUnit.setText(String.valueOf(item.getUnit()));
//        editCartItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Master.addDialog(item);
//            }
//        });

        /*removeFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Master.removefrom_cart(position);
                remove(position, Master.cAdapter);
                Master.totalCost -= Double.parseDouble(String.valueOf(viewHolder.itemCost.getText()));

            }
        });*/

    }

    public void add(CartItemsClass item){
        listitems.add(item);
        notifyItemInserted(listitems.indexOf(item));
        Master.totalCost += item.getQuantity()*(Double.parseDouble(item.getCartitemprice()));
        Message msg = new Message();
        msg.arg1 = 1;
        Master.updateCartCostHandler.sendMessage(msg);
    }

    public void remove(int position,CartRecyclerViewAdapter c){
        listitems.remove(position);
        Log.i("CartItems Length", String.valueOf(Master.cartitems.size()));
        Master.cAdapter = null;
        Master.cAdapter = new CartRecyclerViewAdapter(listitems, context);
        Master.cartItemRecyclerView.setAdapter(Master.cAdapter);
        Message msg = new Message();
        msg.arg1 = 1;
        Master.updateCartCostHandler.sendMessage(msg);
//       c.notifyDataSetChanged();
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, listitems.size()-position+1);

    }

    public void emptyCart(){
        Master.totalCost = 0;
        listitems.clear();
        Master.cAdapter = null;
        Master.cAdapter = new CartRecyclerViewAdapter(listitems, context);
        Master.cartItemRecyclerView.setAdapter(Master.cAdapter);
    }

    public int getItemCount() {
        return listitems == null ? 0 : listitems.size();
    }

}

