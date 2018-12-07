package com.webmob.Restaurant_Yab.pages;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.webmob.Restaurant_Yab.R;
import com.webmob.Restaurant_Yab.model.RestaurantTO;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ali on 24/01/2018.
 */
public class RestaurantDetail extends Activity {
    TextView textViewName;
    TextView textViewTel;
    TextView textViewAddress;
    ImageView imageViewPicDetail;

    ImageLoadingListener animateFirstListener;
    DisplayImageOptions options;
    public ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_detail);

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewTel = (TextView) findViewById(R.id.textViewTel);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        imageViewPicDetail = (ImageView) findViewById(R.id.imageViewPicDetail);

        RestaurantTO restaurantTO = (RestaurantTO) getIntent().getSerializableExtra("restaurant");

        textViewName.setText(restaurantTO.getName());
        textViewTel.setText(restaurantTO.getTel());
        textViewAddress.setText(restaurantTO.getAddress());

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
            animateFirstListener = new AnimateFirstDisplayListener();
            imageLoader.displayImage(restaurantTO.getPic(), imageViewPicDetail, options, animateFirstListener);
        } catch (Exception ex) {
            Log.e("ERROR IMAGE LOADER : ", ex + "");
            Toast.makeText(getApplicationContext(), ex + "IMAGE", Toast.LENGTH_LONG);

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