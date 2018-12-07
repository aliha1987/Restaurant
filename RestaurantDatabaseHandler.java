package com.webmob.Restaurant_Yab.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 24/01/2018.
 */
public class RestaurantDatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "restaurantYab";
    private static final String RESTAURANT_TABLE = "RESTAURANT";
    private static final String RESTAURANT_ID = "id";
    private static final String RESTAURANT_NAME = "name";
    private static final String RESTAURANT_ADDRESS = "address";
    private static final String RESTAURANT_TEL = "tel";
    private static final String RESTAURANT_PIC = "pic";

    public RestaurantDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            String createRestaurantTable = "CREATE TABLE IF NOT EXISTS " + RESTAURANT_TABLE + "(" +
                    RESTAURANT_ID + " TEXT," +
                    RESTAURANT_NAME + " TEXT," +
                    RESTAURANT_ADDRESS + " TEXT," +
                    RESTAURANT_TEL + " TEXT," +
                    RESTAURANT_PIC + " TEXT " + ")";
            sqLiteDatabase.execSQL(createRestaurantTable);
        } catch (Exception e) {
            Log.e("EXCEPTION ", e + "");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addRestaurant(RestaurantTO restaurantTO) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(RESTAURANT_ID, restaurantTO.getId());
            values.put(RESTAURANT_NAME, restaurantTO.getName());
            values.put(RESTAURANT_ADDRESS, restaurantTO.getAddress());
            values.put(RESTAURANT_TEL, restaurantTO.getTel());
            values.put(RESTAURANT_PIC, restaurantTO.getPic());

            db.insert(RESTAURANT_TABLE, null, values);
            db.close();
        } catch (Exception e) {
            Log.e("EXCEPTION INSERT ", e + "");
        }
    }

    public List<RestaurantTO> getAllRestaurant() {
        List<RestaurantTO> restaurantTOs = new ArrayList<RestaurantTO>();
        String selectQuery = "SELECT * FROM " + RESTAURANT_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    RestaurantTO restaurantTO = new RestaurantTO();
                    restaurantTO.setId(cursor.getString(0));
                    restaurantTO.setName(cursor.getString(1));
                    restaurantTO.setAddress(cursor.getString(2));
                    restaurantTO.setTel(cursor.getString(3));
                    restaurantTO.setPic(cursor.getString(4));

                    restaurantTOs.add(restaurantTO);
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Exception", e + "");
        }
        return restaurantTOs;
    }


}

