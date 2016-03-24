package com.example.prasanna.myapplication;

import android.app.Activity;

import android.app.Fragment;

import android.os.Bundle;


import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

public class RestaurantPagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private ArrayList<Restaurant> mRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mRestaurants = RestaurantLab.get(this).getRestaurants();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return mRestaurants.size();
            }

            @Override
            public android.support.v4.app.Fragment getItem(int pos) {
                Restaurant restaurant = mRestaurants.get(pos);
                return DetailActivity.newInstance(restaurant.getRestId());
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) { }

            public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) { }

            public void onPageSelected(int pos) {
                Restaurant crime = mRestaurants.get(pos);
                if (crime.getTitle() != null) {
                    setTitle(crime.getTitle());

                }
            }
        });

        String restId = (String)getIntent().getSerializableExtra("args");
        for (int i = 0; i < mRestaurants.size(); i++) {
            if (mRestaurants.get(i).getRestId().equals(restId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }


    }
}
