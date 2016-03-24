package com.example.prasanna.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailActivity extends Fragment {
    private Restaurant mRestaurant;
    private String restaurant;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restaurant = getArguments() != null ? getArguments().getString("args") : "New";
        mRestaurant=RestaurantLab.get(getActivity()).getRestaurant(restaurant);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.restaurant_detail_layout, container, false);


        TextView detail0TextView =(TextView)rootView.findViewById(R.id.detail_0);
        detail0TextView.setText(mRestaurant.getRestId()+"_0");
        TextView detail1TextView =(TextView)rootView.findViewById(R.id.detail_1);
        detail1TextView.setText(mRestaurant.getRestId() + "_1");
        TextView detail2TextView =(TextView)rootView.findViewById(R.id.detail_2);
        detail2TextView.setText(mRestaurant.getRestId() + "_2");

        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        getActivity().getActionBar().setHomeButtonEnabled(true);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action buttons
        switch(item.getItemId()) {
            case android.R.id.home:
                if(NavUtils.getParentActivityName(getActivity())!=null){
                    getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Fragment newInstance(String restId) {
        Fragment fragment = new DetailActivity();
        Bundle args = new Bundle();
        args.putString("args", restId);
        fragment.setArguments(args);
        return fragment;
    }
}
