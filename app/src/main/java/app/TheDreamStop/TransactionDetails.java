package app.TheDreamStop;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import OrderHistory.TransactionDetailsClass;
import OrderHistory.TransactionDetailsRecyclerViewAdapter;


public class TransactionDetails extends ActionBarActivity {

    Intent getIntent;
    String JSONString;
    TextView id, status;
    ArrayList<TransactionDetailsClass> transactionDetailsClass;
    TransactionDetailsRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4caf50")));
        bar.setTitle("Transaction Details");

        id = (TextView) findViewById(R.id.transaction_details_id);
        status = (TextView) findViewById(R.id.transaction_details_status);

        getIntent = getIntent();
        JSONString = getIntent.getStringExtra("JSON");

        transactionDetailsClass = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.transaction_details_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(TransactionDetails.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        try {
            JSONObject tempJSON = new JSONObject(JSONString);
            id.setText(tempJSON.getString("TID"));
            status.setText(tempJSON.getString("status"));

            JSONArray tempArray = tempJSON.getJSONArray("transaction");

            String name, price, quantity, totalPrice;

            for(int i=0;i<tempArray.length();i++) {
                JSONObject transaction = tempArray.getJSONObject(i);
                name = transaction.getString("Name");
                price = transaction.getString("Price");
                quantity = transaction.getString("Quantity");
                totalPrice = transaction.getString("Net Price");

                transactionDetailsClass.add(new TransactionDetailsClass(name, price, quantity, totalPrice));
            }

            adapter = new TransactionDetailsRecyclerViewAdapter(transactionDetailsClass, TransactionDetails.this);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transaction_details, menu);
        return true;
    }

    @Override
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
    }
}
