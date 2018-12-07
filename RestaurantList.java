package com.webmob.Restaurant_Yab.pages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.webmob.Restaurant_Yab.R;
import com.webmob.Restaurant_Yab.model.RestaurantDatabaseHandler;
import com.webmob.Restaurant_Yab.model.RestaurantTO;

import java.util.*;

/**
 * Created by Ali on 24/01/2018.
 */
public class RestaurantList extends Activity {
    ListView listViewRestaurants;
    RestaurantDatabaseHandler restaurantDatabaseHandler;
    SQLiteDatabase sqLiteDatabase;
    List<RestaurantTO> restaurantTOList;

    AnimateFirstDisplayListener animateFirstDisplayListener;
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;

    RestaurantAdapter restaurantAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_list);

        listViewRestaurants = (ListView) findViewById(R.id.listViewRestaurant);
        restaurantTOList = new ArrayList<RestaurantTO>();
        sqLiteDatabase = openOrCreateDatabase("restaurantYab", MODE_PRIVATE, null);
        restaurantDatabaseHandler = new RestaurantDatabaseHandler(this);
        restaurantTOList = restaurantDatabaseHandler.getAllRestaurant();

        restaurantAdapter = new RestaurantAdapter(getApplicationContext());
        listViewRestaurants.setAdapter(restaurantAdapter);

        listViewRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(RestaurantList.this, RestaurantDetail.class);
                intent.putExtra("restaurant", restaurantTOList.get(position));
                startActivity(intent);
            }
        });

        try {
            imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
            options = new DisplayImageOptions.Builder()
                    //.showImageOnLoading(R.drawable.ic_stub)
                    //.showImageForEmptyUri(R.drawable.ic_empty)
                    // .showImageOnFail(R.drawable.ic_error)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .displayer(new RoundedBitmapDisplayer(170))
                    .build();
            animateFirstDisplayListener = new AnimateFirstDisplayListener();
        } catch (Exception e) {
            Log.e("ImageLoader Exception", e + " ");
        }
    }

    public class RestaurantAdapter extends BaseAdapter {

        Context context;
        LayoutInflater inflater;

        public RestaurantAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return restaurantTOList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            TextView textViewName;
            ImageView imageView;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.restaurant_items, null);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewRes);

            textViewName.setTag(position);
            imageView.setTag(position);

            textViewName.setText(restaurantTOList.get(position).getName());
            try {
                imageLoader.displayImage(restaurantTOList.get(position).getPic(), imageView, options, animateFirstDisplayListener);
            } catch (Exception e) {
                Log.e("Inflation Exception", e + " ");
            }

            return itemView;
        }
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}