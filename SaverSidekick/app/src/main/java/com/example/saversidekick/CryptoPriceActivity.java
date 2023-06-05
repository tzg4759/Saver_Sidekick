package com.example.saversidekick;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// activity which connects to coincap api and shows response in a list view for the user to scroll through and check
public class CryptoPriceActivity extends AppCompatActivity {
    private ListView cryptoPriceList;       // list view object for the crypto prices
    private Button homeButton;      // back button to go back to the previous page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_price);

        cryptoPriceList = findViewById(R.id.listView);      // initialise list view object
        homeButton = findViewById(R.id.homebutton);     // initialise home button

        // set home button so that it navigates to MainActivity when clicked
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // use FetchAPIData class to fetch the crypto price response
        FetchAPIData fetchAPIData = new FetchAPIData();
        fetchAPIData.execute();     // show data
    }


    // FetchAPIData class which brings the data from the coincap API and passes it to be displayed in a listview
    private class FetchAPIData extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            List<String> result = new ArrayList<>();

            try {
                URL coincapurl = new URL("https://api.coincap.io/v2/assets");
                urlConnection = (HttpURLConnection) coincapurl.openConnection();
                urlConnection.setRequestMethod("GET");

                StringBuilder buffer = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                // JSON resonse object/JSON response array
                JSONObject response = new JSONObject(buffer.toString());
                JSONArray responseArray = response.getJSONArray("data");

                //iterate through JSON response array and save responses as concatenated strings
                for (int i = 0; i < responseArray.length(); i++) {
                    JSONObject dataObject = responseArray.getJSONObject(i);
                    String name = dataObject.getString("name");     // get coin name
                    Double price = dataObject.getDouble("priceUsd");        // get coin price in USD
                    Double change24hours = dataObject.getDouble("changePercent24Hr");       // get % change in 24 hours
                    String symbol = dataObject.getString("symbol");     // get coin code/symbol
                    //create list entry string -- convert doubles to strings and format to 2 decimal places
                    result.add("\n" + symbol + ": " +name + ": $" +  String.format("%.2f", price) + "\n% Change 24h: " + String.format("%.2f", change24hours) + "\n");
                }
            } catch (IOException | JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Exception", Toast.LENGTH_LONG).show();        // IOE exception JSON
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();     // close connection
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "IOException", Toast.LENGTH_LONG).show();       // IOE exception in reader
                    }
                }
            }

            return result;      // return string of API response
        }

        // function to create adapter to show entries in the listview
        @Override
        protected void onPostExecute(List<String> result) {
            //create new array adapter to convert api data result to listview
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CryptoPriceActivity.this, android.R.layout.simple_list_item_1, result) {
                // set the view for the list
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView textView = view.findViewById(android.R.id.text1);
                    textView.setTextSize(18);       // set text size for list view entries
                    return view;
                }
            };
            cryptoPriceList.setAdapter(adapter);        // set the adapter to show the crypto data text
        }
    }
}
