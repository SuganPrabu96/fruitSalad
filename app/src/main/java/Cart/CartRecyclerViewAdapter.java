package Cart;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
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
    private static Handler editHandler;
    //private ImageButton removeFromCart;
    //private ImageButton editCartItem;

    public CartRecyclerViewAdapter(ArrayList<CartItemsClass> items,Context context){
        this.listitems = items;
        this.context = context;
    }

    public CartCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_card, parent, false);
        CartCardViewHolder vH = new CartCardViewHolder(context, v);

        editHandler = new Handler();

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

        viewHolder.itemMultiplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog(position);
            }
        });

        editHandler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.arg1==1){
                    Bundle b = msg.getData();
                    String qua = b.getString("qua");
                    Integer pos = b.getInt("pos");

                    CartItemsClass itemTemp = listitems.get(pos);

                    Master.totalCost -= itemTemp.getQuantity()*Float.parseFloat(itemTemp.getCartitemprice());

                    itemTemp.quantity = Float.parseFloat(qua);

                    //viewHolder.itemMultiplier.setText("X " + String.valueOf(itemTemp.getQuantity()));
                    //viewHolder.itemTotalCost.setText("Rs. " + String.valueOf(itemTemp.getQuantity()*Float.parseFloat(itemTemp.getCartitemprice())));

                    Master.cAdapter.notifyItemChanged(pos);

                    Master.totalCost += Double.parseDouble(String.valueOf(itemTemp.getQuantity()*Float.parseFloat(itemTemp.getCartitemprice())));

                    Message msg1 = new Message();
                    msg1.arg1 = 1;
                    Master.updateCartCostHandler.sendMessage(msg1);
                }
            }
        };

    }

    public static void editDialog(final int position){
        Master.editDialog.setTitle("Select Quantity");
        Master.editDialog.setContentView(R.layout.edit_dialog);
        Master.editDialog.setCancelable(true);
        Master.editDialog.show();
        Log.d("pos", String.valueOf(position));
        CartItemsClass item = listitems.get(position);

        final String[] floatitems={"0.5","1","1.5","2","2.5","3","3.5","4","4.5","5","5.5","6","6.5","7","7.5","8","8.5","9","9.5","10"};
        final String[] intitems={"1","2","3","4","5","6","7","8","9","10"};
        final NumberPicker np = (NumberPicker) Master.editDialog.findViewById(R.id.numberPickerEdit);
        np.setMaxValue(10);
        np.setMinValue(1);
        np.setWrapSelectorWheel(false);
        if(item.getChangeable()=='n'){
            np.setDisplayedValues(intitems);
            Log.i("value of cb ","cb is n");}
        else if(item.getChangeable()=='y')
        {
            np.setDisplayedValues(floatitems);
            Log.i("value of cb","cb is y");

        }

        //   final int i = np.getValue()-1;
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.i("qty",intitems[np.getValue()-1]);
                Log.i("value check",String.valueOf(np.getValue()));
            }
        });


        //    Log.i("qty",num[i]);

        //     np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        Button btn1 = (Button) Master.editDialog.findViewById(R.id.cart_item_quan_edit_btn);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //      Master.addtocart_fn(item);
                //  addtocart_fn(cartitem);
                //Log.d("Value of Qty","check");
                //    Log.i("qty",num[i]);
                Log.i("qty inside ", floatitems[np.getValue() - 1]);

                Bundle b = new Bundle();
                b.putString("qua", String.valueOf(np.getValue()));
                b.putInt("pos",position);
                Message msg = new Message();
                msg.arg1 = 1;
                msg.setData(b);
                editHandler.sendMessage(msg);

                Master.editDialog.dismiss();
            }
        });

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
        Message msg = new Message();
        msg.arg1 = 1;
        Master.updateCartCostHandler.sendMessage(msg);
        Master.cAdapter = null;
        Master.cAdapter = new CartRecyclerViewAdapter(listitems, context);
        Master.cartItemRecyclerView.setAdapter(Master.cAdapter);
    }

    public int getItemCount() {
        return listitems == null ? 0 : listitems.size();
    }

}

