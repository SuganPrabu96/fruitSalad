package ItemDisplay;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;

import app.TheDreamStop.ItemsSearchActivity;
import app.TheDreamStop.Master;
import app.TheDreamStop.R;

/**
 * Created by Suganprabu on 18-04-2015.
 */
public class ItemsCardAdapter extends RecyclerView.Adapter<ViewHolderItems>{

    private ArrayList<ItemDetailsClass> items;
    private Context context;
    private Button addBtn;
    private FrameLayout itemCardFrame;
    private ImageView newItemTag;
    private String callingActivity;

    public ItemsCardAdapter(ArrayList<ItemDetailsClass> items, Context context, String callingActivity) {
        this.items = items;
        this.callingActivity = callingActivity;
        this.context = context;
    }

    @Override
    public ViewHolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items_card_front, parent, false);
        final ViewHolderItems vH = new ViewHolderItems(context, v);

        itemCardFrame = (FrameLayout) v.findViewById(R.id.item_card_frame);
        addBtn = (Button) v.findViewById(R.id.buttonAdd);
        newItemTag = (ImageView) v.findViewById(R.id.new_item_tag);

        return vH;
    }

    @Override
    public void onBindViewHolder(final ViewHolderItems viewHolder, final int position) {
        final ItemDetailsClass item = items.get(position);

        Log.d("itemPrice", String.valueOf(item.getItemprice()));

        viewHolder.itemname.setText(item.getItemtitle());
//        viewHolder.itemimg.setImageResource(Integer.parseInt(item.getItemimgurl()));
        viewHolder.selPrice.setText(item.getItemprice().toString());

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (callingActivity.equals("master"))
                    Master.addDialog(item);
                else if (callingActivity.equals("search"))
                    ItemsSearchActivity.addDialog(item);
            }
        });

        itemCardFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.arg1 = 1;
                Bundle b = new Bundle();
                b.putString("name",item.getItemtitle());
                b.putDouble("price", item.getItemprice());
                b.putDouble("MRP",item.getItemMRP());
                b.putInt("PID",item.getProductid());
                b.putString("unit",item.getUnit());
                b.putFloat("quantity",item.getQty());
                b.putInt("q",item.getQ());
                b.putChar("changeable",item.getChangeable());
                msg.setData(b);
                Master.itemDetailsHandler.sendMessage(msg);
            }
        });

        if(Master.newProductsID.contains(item.getProductid()))
            newItemTag.setVisibility(View.VISIBLE);
        else
            newItemTag.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

}

