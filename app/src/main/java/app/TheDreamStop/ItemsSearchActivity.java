package app.TheDreamStop;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Cart.CartRecyclerViewAdapter;
import ItemDisplay.ItemDetailsClass;
import ItemDisplay.ItemsCardAdapter;
import util.SearchSuggestionProvider;
import util.ServiceHandler;


public class ItemsSearchActivity extends ActionBarActivity {

    private String searchQuery, SearchReturnedJSON;
    private final String searchURL="http://thedreamstop.in/api/search.php";
    public static SearchRecentSuggestions recentSuggestions;
    ArrayList<ItemDetailsClass> listOfItems;
    ArrayList<String> Name, unit;
    ArrayList<Double> price;
    ArrayList<Double> MRP;
    ArrayList<Integer> cID, sID, pID, q;
    ArrayList<Float> quantity;
    ArrayList<Character> changeable;
    ItemsCardAdapter mAdapter;
    RecyclerView mRecyclerView;
    static Handler searchHandler;
    private ImageView checkoutButton;
    private TextView cartTotal;
    private RecyclerView cartItemRecyclerView;
    private static Dialog addtocartDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_search);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("The Dream Stop");
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4caf50")));

        addtocartDialog = new Dialog(ItemsSearchActivity.this);

        checkoutButton = (ImageView) findViewById(R.id.checkoutbutton);
        cartTotal = (TextView) findViewById(R.id.cart_totalcost);

        cartTotal.setText(String.valueOf(Master.totalCost));

        cartItemRecyclerView = (RecyclerView) findViewById(R.id.cart_items_recyclerview);
        cartItemRecyclerView.setHasFixedSize(false);
        cartItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartItemRecyclerView.setItemAnimator(new DefaultItemAnimator());

        Master.cAdapter = new CartRecyclerViewAdapter(Master.cartitems, getApplicationContext());

        cartItemRecyclerView.setAdapter(Master.cAdapter);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Master.checkoutDialog(getApplicationContext());
            }
        });

        Master.updateCartCostHandler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.arg1==1){
                    cartTotal.setText(String.valueOf(Master.totalCost));
                }
            }
        };

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            searchQuery = intent.getStringExtra(SearchManager.QUERY);
            recentSuggestions = new SearchRecentSuggestions(this, SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
            recentSuggestions.saveRecentQuery(searchQuery,null);
        }

        listOfItems = new ArrayList<>();

        new Search().execute(searchQuery);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_search);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        searchHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.arg1 == 1) {

                    Log.i("listOfItems", listOfItems.toString());

                    mAdapter = new ItemsCardAdapter(listOfItems,getApplicationContext(),"search");
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        };

    }


    private class Search extends AsyncTask<String,Void,String>{

        private ProgressDialog p1 = new ProgressDialog(ItemsSearchActivity.this);
        private boolean searchSuccess = false;

        @Override
        protected void onPreExecute(){

            p1.setTitle("Searching...");
            p1.setCancelable(false);
            p1.show();

        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> paramsSearch = new ArrayList<>();

            paramsSearch.add(new BasicNameValuePair("session",LoginActivity.prefs.getString("session","")));
            paramsSearch.add(new BasicNameValuePair("q",params[0]));

            ServiceHandler jsonParser = new ServiceHandler();

            SearchReturnedJSON = jsonParser.makeServiceCall(searchURL, ServiceHandler.POST, paramsSearch);

            if(SearchReturnedJSON!=null){

                try {
                    JSONObject searchJSON = new JSONObject(SearchReturnedJSON);

                    if(searchJSON.getString("success").equals("true")) {

                        searchSuccess = true;
                        JSONArray searchJSONArray = new JSONArray(String.valueOf(searchJSON.getJSONArray("items")));

                        Name = new ArrayList<>();
                        MRP = new ArrayList<>();
                        price = new ArrayList<>();
                        cID = new ArrayList<>();
                        sID = new ArrayList<>();
                        pID = new ArrayList<>();
                        unit = new ArrayList<>();
                        q = new ArrayList<>();
                        changeable = new ArrayList<>();
                        quantity = new ArrayList<>();

                        for (int i = 0; i < searchJSONArray.length(); i++) {
                            JSONObject temp = searchJSONArray.getJSONObject(i);
                            Name.add(temp.getString("name"));
                            MRP.add(temp.getDouble("MRP"));
                            price.add(temp.getDouble("price"));
                            cID.add(temp.getInt("catID"));
                            sID.add(temp.getInt("subCatID"));
                            pID.add(temp.getInt("PID"));
                            unit.add(temp.getString("unit"));
                            q.add(temp.getInt("q"));
                            changeable.add(temp.getString("cb").charAt(0));
                            quantity.add(Float.valueOf(temp.getString("qty")));
                        }
                        listOfItems = new ArrayList<>();

                        for (int i = 0; i < searchJSONArray.length(); i++) {
                            listOfItems.add(new ItemDetailsClass(Name.get(i), price.get(i), MRP.get(i), pID.get(i), quantity.get(i), unit.get(i), changeable.get(i), q.get(i)));
                        }
                    }

                    else
                        searchSuccess = false;

                    Log.i("searchJSON",searchJSON.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result){

            if(p1!=null && p1.isShowing()){
                p1.cancel();
                p1.hide();
            }

            if(searchSuccess) {
                Message msg = new Message();
                msg.arg1 = 1;
                ItemsSearchActivity.searchHandler.sendMessage(msg);
            }

        }
    }

    public static void addDialog(ItemDetailsClass item1)
    {
        final ItemDetailsClass item = item1;


        Log.d("item1 PID", String.valueOf(item1.getProductid()));

        addtocartDialog.setTitle("Select Quantity");
        addtocartDialog.setContentView(R.layout.add_dialog);
        addtocartDialog.show();
        final String[] floatitems={"0.5","1","1.5","2","2.5","3","3.5","4","4.5","5","5.5","6","6.5","7","7.5","8","8.5","9","9.5","10"};
        final String[] intitems={"1","2","3","4","5","6","7","8","9","10"};
        final NumberPicker np = (NumberPicker) addtocartDialog.findViewById(R.id.numberPicker1);
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

        Button btn1 = (Button) addtocartDialog.findViewById(R.id.btn_to_add);
        Button btn2 = (Button) addtocartDialog.findViewById(R.id.btn_to_cancel);


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


                addtocartDialog.dismiss();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  addtocartDialog.hide();
                addtocartDialog.dismiss();
            }
        });


    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_items_search, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
