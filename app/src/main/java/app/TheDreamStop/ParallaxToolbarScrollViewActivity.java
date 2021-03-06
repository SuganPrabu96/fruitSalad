package app.TheDreamStop; /**
 * Created by srikrishna on 15-04-2015.
 */


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
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
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ItemDisplay.ItemDetailsClass;
import util.ActivityAnimator;
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
    private static String itemsURLReturnedJSON, itemDescReturnedJSON;
    private static final String itemsImagesURL = "http://thedreamstop.in/api/prodImage.php";
    private static final String itemsDescURL = "http://thedreamstop.in/api/productInfo.php";
    private WifiManager wifiManager;
    private Handler itemDescHandler;
    ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallaxtoolbarscrollview);
        addtocartD = new Dialog(ParallaxToolbarScrollViewActivity.this);

        bar = getSupportActionBar();
      //  bar.setCustomView(findViewById(R.id.toolbar));

        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4caf50")));

        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        //mImageView = findViewById(R.id.image);

        itemDescHandler = new Handler();

        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels / 2;
        height = displayMetrics.heightPixels / 4;
        getIntent = getIntent();
        mImageView = findViewById(R.id.itemdetail_name);
 //       mToolbarView = findViewById(R.id.toolbar);
        itemDescription = (TextView) findViewById(R.id.parallaxItemDescription);
        itemName = (TextView) findViewById(R.id.itemdetail_name);
        itemPrice = (TextView) findViewById(R.id.itemdetail_price);
        itemImage = (ImageView) findViewById(R.id.itemdetail_image);
        addToCart = (Button) findViewById(R.id.itemdetail_addbutton);
//        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));

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
        bar.setTitle(name);

        new ItemsDesc().execute(String.valueOf(pID));

        itemDescHandler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.arg1==1){
                    itemDescription.setText(msg.getData().getString("desc"));
                }
            }
        };

     //   itemName.setText(name);
        itemPrice.setText(String.valueOf(price));
        final ItemDetailsClass item = new ItemDetailsClass(name,price,MRP,pID,quantity,unit,changeable,q) ;

        //itemDescription.setText(getIntent.getExtras().getString("Description").toString());

        if(LoginActivity.prefs.getString("downloadImagesOverWifi","").equals("Y")){
            if(wifiManager.isWifiEnabled())
                new LoadProductImages().execute(String.valueOf(pID));
        }
        else
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


        Log.d("item1 PID", String.valueOf(item1.getProductid()));

        addtocartD.setTitle("Select Quantity");
        addtocartD.setContentView(R.layout.add_dialog);
        addtocartD.show();
        final String[] floatitems={"0.5","1","1.5","2","2.5","3","3.5","4","4.5","5","5.5","6","6.5","7","7.5","8","8.5","9","9.5","10"};
        final String[] intitems={"1","2","3","4","5","6","7","8","9","10"};
        final NumberPicker np = (NumberPicker) addtocartD.findViewById(R.id.numberPicker1);
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

        Button btn1 = (Button) addtocartD.findViewById(R.id.btn_to_add);
        Button btn2 = (Button) addtocartD.findViewById(R.id.btn_to_cancel);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //      Master.addtocart_fn(item);
                //  addtocart_fn(cartitem);
                //Log.d("Value of Qty","check");
                //    Log.i("qty",num[i]);
                Log.i("qty inside ",floatitems[np.getValue()-1]);
                if(item.getChangeable()=='y') {
                    Master.addtocart_fn(item.getItemtitle(),  Float.parseFloat(floatitems[np.getValue() - 1]), item.getItemprice().toString(), item.getProductid(), item.getQ(), item.getUnit(), item.getChangeable());
                }
                else if(item.getChangeable()=='n') {
                    Master.addtocart_fn(item.getItemtitle(),Float.parseFloat(intitems[np.getValue()-1]) , item.getItemprice().toString(), item.getProductid(), item.getQ() , item.getUnit(), item.getChangeable());

                }


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
//        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

        if (scrollState == ScrollState.UP) {
           if(bar.isShowing()){bar.hide();}
        } else if (scrollState == ScrollState.DOWN) {
            if(!bar.isShowing()){bar.show();}
        }
    }

    private class ItemsDesc extends AsyncTask<String,Void,Void>{

        private String desc;
        private boolean success = false;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String...params){

            Log.i("Inside Background", "True");

            List<NameValuePair> paramsItems = new ArrayList<NameValuePair>();

            paramsItems.add(new BasicNameValuePair("PID", params[0]));

            ServiceHandler jsonParser = new ServiceHandler();
            itemDescReturnedJSON = jsonParser.makeServiceCall(itemsDescURL, ServiceHandler.POST, paramsItems);
            if (itemDescReturnedJSON != null) {
                try {
                    Log.i("itemDescReturnedJSON", itemDescReturnedJSON);
                    JSONObject itemsJSON = new JSONObject(itemDescReturnedJSON);
                    if (itemsJSON.getString("success").equals("true")) {
                        desc = itemsJSON.getString("description");
                        success = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);

            if(success){
                Message msg = new Message();
                msg.arg1=1;
                Bundle b = new Bundle();
                b.putString("desc",desc);
                msg.setData(b);
                itemDescHandler.sendMessage(msg);
            }

        }
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
                  //  Log.i("itemsURLReturnedJSON",itemsURLReturnedJSON);
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

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        new ActivityAnimator().fadeAnimation(ParallaxToolbarScrollViewActivity.this);
    }


}