package com.webmob.Restaurant_Yab;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.webmob.Restaurant_Yab.model.RestaurantDatabaseHandler;
import com.webmob.Restaurant_Yab.model.RestaurantTO;
import com.webmob.Restaurant_Yab.pages.AboutUs;
import com.webmob.Restaurant_Yab.pages.RestaurantList;
import com.webmob.Restaurant_Yab.pages.RestaurantSearch;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
    Intent intent;
    ImageView imageViewRestaurantList;
    ImageView imageViewRestaurantSearch;
    ImageView imageViewAboutUs;
    ImageView imageViewExit;
    String result;

    RestaurantDatabaseHandler restaurantDatabaseHandler;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<RestaurantTO> restaurantTOs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        sqLiteDatabase = openOrCreateDatabase("restaurantYab", MODE_PRIVATE, null);
        restaurantDatabaseHandler = new RestaurantDatabaseHandler(this);
        restaurantDatabaseHandler.onCreate(sqLiteDatabase);

        restaurantTOs = new ArrayList<RestaurantTO>();

        imageViewRestaurantList = (ImageView) findViewById(R.id.imageViewRestaurantList);
        imageViewRestaurantList.setOnClickListener(this);
        imageViewRestaurantSearch = (ImageView) findViewById(R.id.imageViewRestaurantSearch);
        imageViewRestaurantSearch.setOnClickListener(this);
        imageViewAboutUs = (ImageView) findViewById(R.id.imageViewAboutUs);
        imageViewAboutUs.setOnClickListener(this);
        imageViewExit = (ImageView) findViewById(R.id.imageViewExit);
        imageViewExit.setOnClickListener(this);

        ParseJSON parseJSON = new ParseJSON();
        parseJSON.execute();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewRestaurantList:
                intent = new Intent(MainActivity.this, RestaurantList.class);
                startActivity(intent);
                break;

            case R.id.imageViewRestaurantSearch:
                intent = new Intent(MainActivity.this, RestaurantSearch.class);
                startActivity(intent);
                break;

            case R.id.imageViewAboutUs:
                intent = new Intent(MainActivity.this, AboutUs.class);
                startActivity(intent);
                break;

            case R.id.imageViewExit:
                System.exit(0);
                break;

            default:
                break;
        }
    }

    public String getJSON(String url) {
        try {
            InputStream inputStream = null;
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            inputStream.close();
            String result = sb.toString();

            return result;
        } catch (Exception ex) {

            Log.e("ppp", ex + "")
            ;
            return null;

        }
    }

    private class ParseJSON extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            result = getJSON("http://kafsaab.com/json.txt");

            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    RestaurantTO restaurant = new RestaurantTO();
                    restaurant.setId(jsonObject.getString("id"));
                    restaurant.setName(jsonObject.getString("name"));
                    restaurant.setAddress(jsonObject.getString("address"));
                    restaurant.setTel(jsonObject.getString("tel"));
                    restaurant.setPic(jsonObject.getString("pic"));

                    restaurantTOs.add(restaurant);
                    restaurantDatabaseHandler.addRestaurant(restaurant);

                }
            } catch (Exception e) {
                Log.e("EXCEPTION :", e + "");
            }
            return null;
        }
    }
}
