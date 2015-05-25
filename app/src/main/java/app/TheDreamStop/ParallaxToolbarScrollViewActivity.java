package app.TheDreamStop; /**
 * Created by srikrishna on 15-04-2015.
 */


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import ItemDisplay.ItemDetailsClass;

public class ParallaxToolbarScrollViewActivity extends ActionBarActivity implements ObservableScrollViewCallbacks {

    private View mImageView;
    private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;
    private TextView itemDescription, itemName, itemPrice;
    private Button addToCart;
    private Intent getIntent;
    private String name,imageURL;
    private double MRP,price;
    public static Dialog addtocartD;
    private int pID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallaxtoolbarscrollview);
        addtocartD = new Dialog(ParallaxToolbarScrollViewActivity.this);

        getSupportActionBar().setCustomView(findViewById(R.id.toolbar));
        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        //mImageView = findViewById(R.id.image);

        getIntent = getIntent();
        mImageView = findViewById(R.id.itemdetail_name);
        mToolbarView = findViewById(R.id.toolbar);
        itemDescription = (TextView) findViewById(R.id.parallaxItemDescription);
        itemName = (TextView) findViewById(R.id.itemdetail_name);
        itemPrice = (TextView) findViewById(R.id.itemdetail_price);
        addToCart = (Button) findViewById(R.id.itemdetail_addbutton);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        name = getIntent.getExtras().getString("name");
        price = getIntent.getExtras().getDouble("price");
        MRP = getIntent.getExtras().getDouble("MRP");
        imageURL = getIntent.getExtras().getString("image");
        pID = getIntent.getExtras().getInt("PID");
        itemName.setText(name);
        itemPrice.setText(String.valueOf(price));
        final ItemDetailsClass item = new ItemDetailsClass(name,imageURL,price,MRP,pID) ;

        //itemDescription.setText(getIntent.getExtras().getString("Description").toString());

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ParallaxToolbarScrollViewActivity.addDialog(item);
            }
        });

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
    }
    public static void addDialog(ItemDetailsClass item1)
    {
        final ItemDetailsClass item = item1;

        addtocartD.setTitle("Select Quantity");
        addtocartD.setContentView(R.layout.add_dialog);
        addtocartD.show();

        final NumberPicker np = (NumberPicker) addtocartD.findViewById(R.id.numberPicker1);
        np.setMaxValue(10);
        np.setMinValue(1);
        np.setWrapSelectorWheel(false);


        Button btn1 = (Button) addtocartD.findViewById(R.id.btn_to_add);
        Button btn2 = (Button) addtocartD.findViewById(R.id.btn_to_cancel);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Master.addtocart_fn(item.getItemtitle(),np.getValue(),item.getItemprice().toString(),item.getProductid());
                Log.e("Value of Qty", String.valueOf(np.getValue()));

                addtocartD.dismiss();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  addtocartDialog.hide();
                addtocartD.dismiss();
            }
        });


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.primary);
        float alpha = 1 - (float) Math.max(0, mParallaxImageHeight - scrollY) / mParallaxImageHeight;
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }



}