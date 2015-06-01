package app.TheDreamStop; /**
 * Created by srikrishna on 15-04-2015.
 */


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ItemDisplay.ItemDetailsClass;
import util.ServiceHandler;

public class ParallaxToolbarScrollViewActivity extends ActionBarActivity implements ObservableScrollViewCallbacks {

    private View mImageView;
    private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;
    private TextView itemDescription, itemName, itemPrice;
    private static ImageView itemImage;
    private Button addToCart;
    private Intent getIntent;
    private String name, unit;
    private double MRP, price;
    private Float quantity;
    public static Dialog addtocartD;
    private int pID, q;
    private Character changeable;
    private DisplayMetrics displayMetrics;
    private static float width, height;
    private static String itemsURLReturnedJSON;
    private static final String itemsImagesURL = "http://thedreamstop.in/api/prodImage.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallaxtoolbarscrollview);
        addtocartD = new Dialog(ParallaxToolbarScrollViewActivity.this);

        getSupportActionBar().setCustomView(findViewById(R.id.toolbar));
        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        //mImageView = findViewById(R.id.image);

        displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels / 2;
        height = displayMetrics.heightPixels / 4;
        getIntent = getIntent();
        mImageView = findViewById(R.id.itemdetail_name);
        mToolbarView = findViewById(R.id.toolbar);
        itemDescription = (TextView) findViewById(R.id.parallaxItemDescription);
        itemName = (TextView) findViewById(R.id.itemdetail_name);
        itemPrice = (TextView) findViewById(R.id.itemdetail_price);
        itemImage = (ImageView) findViewById(R.id.itemdetail_image);
        addToCart = (Button) findViewById(R.id.itemdetail_addbutton);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        name = getIntent.getExtras().getString("name");
        price = getIntent.getExtras().getDouble("price");
        MRP = getIntent.getExtras().getDouble("MRP");
        pID = getIntent.getExtras().getInt("PID");
        unit = getIntent.getExtras().getString("unit");
        quantity = getIntent.getExtras().getFloat("quantity");
        q = getIntent.getExtras().getInt("q");
        changeable = getIntent.getExtras().getChar("changeable");
        itemName.setText(name);
        itemPrice.setText(String.valueOf(price));
        final ItemDetailsClass item = new ItemDetailsClass(name,price,MRP,pID,quantity,unit,changeable,q) ;

        //itemDescription.setText(getIntent.getExtras().getString("Description").toString());

        new LoadProductImages().execute(String.valueOf(pID));

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

                Master.addtocart_fn(item.getItemtitle(),np.getValue(),item.getItemprice().toString(),item.getProductid(),item.getQ(),item.getQty(),item.getUnit(),item.getChangeable());
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

    private static class LoadProductImages extends AsyncTask<String, String, Bitmap> {

        Bitmap image;
        @Override
        protected void onPreExecute() {
            Log.i("Inside PreExecute", "True");
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Log.i("Inside Background", "True");

            List<NameValuePair> paramsItems = new ArrayList<NameValuePair>();

            paramsItems.add(new BasicNameValuePair("PID",params[0]));
            paramsItems.add(new BasicNameValuePair("width", String.valueOf(width)));
            paramsItems.add(new BasicNameValuePair("height", String.valueOf(height)));
            ServiceHandler jsonParser = new ServiceHandler();
            itemsURLReturnedJSON = jsonParser.makeServiceCall(itemsImagesURL, ServiceHandler.GET, paramsItems);
            if (itemsURLReturnedJSON != null) {
                try{
                    Log.i("itemsURLReturnedJSON",itemsURLReturnedJSON);
                    URL url = new URL("http://thedreamstop.in/api/prodImage.php?PID="+params[0]+"&width="+String.valueOf(width)+"&height="+String.valueOf(height));
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            Log.i("Inside PostExecute", "True");
            super.onPostExecute(result);
            itemImage.setImageBitmap(image);
        }

    }


}