package com.example.prasanna.myapplication;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Prasanna on 3/21/16.
 */
public class RestaurantLab {
    private static RestaurantLab sPlanetLab;
    private Context mAppContext;
    private ArrayList<Restaurant> mRestaurants;

    private RestaurantLab(Context appContext) {
        mAppContext = appContext;
        mRestaurants = new ArrayList<Restaurant>();
//        String[] mPlanetTitles = {"Mercury","Venus","Earth","Mars","Jupiter","Saturn","Uranus",
//                "Neptune","Pluto"};
//        for (int i = 0; i < mPlanetTitles.length; i++) {
//            Restaurant c = new Restaurant(mPlanetTitles[i%mPlanetTitles.length]);
//            c.setTitle(mPlanetTitles[i%mPlanetTitles.length]);
//            c.setFavorite(i % 2 == 0); // Every other one
//            mRestaurants.add(c);
//        }
    }
    public void addRestaurant(Restaurant c) {
        mRestaurants.add(c);
    }


    public static RestaurantLab get(Context c) {
        if (sPlanetLab == null) {
            sPlanetLab = new RestaurantLab(c.getApplicationContext());
        }
        return sPlanetLab;
    }

    public ArrayList<Restaurant> getRestaurants() {
        return mRestaurants;
    }

    public Restaurant getRestaurant(String id) {
        for (Restaurant c : mRestaurants) {
            if (c.getRestId().equals(id))
                return c;
        }
        return null;
    }
}
