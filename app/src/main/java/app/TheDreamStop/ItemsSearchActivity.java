package app.TheDreamStop;

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
import android.widget.ImageView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_search);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("The Dream Stop");
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4caf50")));

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

                    mAdapter = new ItemsCardAdapter(listOfItems,getApplicationContext());
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
