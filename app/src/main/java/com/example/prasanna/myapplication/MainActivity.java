package com.example.prasanna.myapplication;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends FragmentActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mRestaurantList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private static ArrayList businessNames;

    private String[] mMenuItems;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        mMenuItems = getResources().getStringArray(R.array.menu_items);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mMenuItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.layout_search_keyword).setVisible(!drawerOpen);
        menu.findItem(R.id.action_loc_picker).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mDrawerToggle.onOptionsItemSelected(item);
        // Handle action buttons
        switch(item.getItemId()) {
            case android.R.id.home:
                if(NavUtils.getParentActivityName(this)!=null){
                    getActionBar().setDisplayHomeAsUpEnabled(true);
//                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            case R.id.action_loc_picker:
                // create intent to perform web search for this planet
//                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
//                intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
//                // catch event that there's no activity to handle intent
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
//                }
                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                    Intent intent = intentBuilder.build(MainActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            final Place place = PlacePicker.getPlace(this, data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            final LatLng location=place.getLatLng();
            String attributions = (String) place.getAttributions();
            Toast.makeText(this, name, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "" + location.latitude,  Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "" + location.longitude,  Toast.LENGTH_SHORT).show();
            new YelpCall(location.latitude,location.longitude).execute();
            // new Yelp(location.latitude,location.longitude,consumerKey, consumerSecret, token, tokenSecret).execute();
            // y.execute();

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private class YelpCall extends AsyncTask<Void, Void, String> {
        double lattitude;
        double longitude;

        public YelpCall(double lattitude, double longitude){
            this.lattitude=lattitude;
            this.longitude=longitude;
        }

        @Override
        protected String doInBackground(Void... params) {
            String consumerKey = "4QjdyyRnTL49geMpV4Wjkw";
            String consumerSecret = "GmruUg45q0il_-NJ9pYQIlhjX0A";
            String token = "oQAB7pcu3PF3jmD--GyTcuJ-8Zyw8Zpn";
            String tokenSecret = "1YpsyXHY9NHDBAOw4PcuAxM6Jfk";
            Yelp yelp = new Yelp(consumerKey, consumerSecret, token, tokenSecret);
            String result=yelp.search("food",this.lattitude,this.longitude);

            return result;
        }


        protected void onPostExecute(String result){
            Log.i("in post", result);
//            MainActivity.this.businessNames = new ArrayList();

            try {
                JSONObject json = new JSONObject(result);
                JSONArray businesses;
                businesses = json.getJSONArray("businesses");
                for (int i = 0; i < businesses.length(); i++) {
                    JSONObject business = businesses.getJSONObject(i);

                    Restaurant restaurant=new Restaurant();
                    restaurant.setRestId(business.getString("name"));
                    restaurant.setTitle(business.getString("name"));
                    restaurant.setRating(business.getLong("rating"));
                    restaurant.setRatingImageURL(business.getString("rating_img_url"));
                    restaurant.setReviewCount(business.getInt("review_count"));
                    restaurant.setDisplayPhone(business.getString("display_phone"));
                    restaurant.setImageURL(business.getString("image_url"));
                    restaurant.setSnippetText(business.getString("snippet_text"));
                    restaurant.setFavorite(false);
                    //check in favorite list, if present set true, else false

                    JSONArray arrAddress=business.getJSONObject("location").getJSONArray("address");
                    String[] arrAdd=new String[arrAddress.length()];
                    String address="";
                    for(int j=0;j<arrAdd.length;j++){
                        address=address+arrAddress.getString(j)+" ";
                    }
                    restaurant.setAddress(address);
                    RestaurantLab.get(getBaseContext()).addRestaurant(restaurant);

                }
                selectItem(0);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        // update the main content by replacing fragments
        switch (position){
            case 0:
                Fragment fragment = new SearchFragment();
                Bundle args = new Bundle();
                args.putInt(SearchFragment.ARG_PLANET_NUMBER, position);
                fragment.setArguments(args);

                FragmentManager fragmentManager = getSupportFragmentManager() ;
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                // update selected item and title, then close the drawer
                mDrawerList.setItemChecked(position, true);
                setTitle(mMenuItems[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
            case 1:
                Toast.makeText(this, "Favorites Clicked", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }


    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public static class SearchFragment extends android.support.v4.app.Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";
        private String[] mPlanetTitles;
        private ListView mRestList;
        private RestaurantAdapter adapter;
        public SearchFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public void onResume() {
            super.onResume();
            ((RestaurantAdapter)adapter).notifyDataSetChanged();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.search_layout, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);

            String menuItem = getResources().getStringArray(R.array.menu_items)[i];
            mRestList= (ListView)rootView.findViewById(R.id.rest_list);


            adapter =new RestaurantAdapter(RestaurantLab.get(getActivity()).getRestaurants());
            mRestList.setAdapter(adapter);
            mRestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i("Ala", "Ikde");
                    Restaurant c = adapter.getItem(position);
                    Toast.makeText(getActivity(), c.getRestId() , Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), RestaurantPagerActivity.class);
                    i.putExtra("args", c.getRestId());
                    startActivity(i);
                }
            });
            getActivity().setTitle(menuItem);
            return rootView;
        }


        private class RestaurantAdapter extends ArrayAdapter<Restaurant> {
            // Restaurant Class
            public RestaurantAdapter(ArrayList<Restaurant> restaurants) {
                super(getActivity(), 0, restaurants);
            }


            @Override
            public Restaurant getItem(int position) {
                return super.getItem(position);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // If we weren't given a view, inflate one
                if (convertView == null) {
                    convertView = getActivity().getLayoutInflater()
                            .inflate(R.layout.list_item_layout, null);
                }

                // Configure the view for this Restaurant
                Restaurant restaurant = getItem(position);

                TextView rest_name=(TextView)convertView.findViewById(R.id.rest_name);
                rest_name.setText(restaurant.getRestId());

                ImageView rest_img = (ImageView)convertView.findViewById(R.id.rest_img);
//                rest_img.setImageURI(Uri.parse(Uri.encode(restaurant.getImageURL())) );
                new DownLoadImageTask(rest_img).execute(restaurant.getImageURL());

                ImageView rest_rating_img=(ImageView)convertView.findViewById(R.id.rest_rating_img);
                new DownLoadImageTask(rest_rating_img).execute(restaurant.getRatingImageURL());

                TextView rest_address=(TextView)convertView.findViewById(R.id.rest_address);
                rest_address.setText(restaurant.getAddress());

                return convertView;
            }

        }
    }

    private static class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try {
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            } catch (Exception e) { // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

    public static class FavoriteFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";
        private String[] mPlanetTitles;
        private ListView mRestList;

        public FavoriteFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.search_layout, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);

            mPlanetTitles = getResources().getStringArray(R.array.planets_array);
            String menuItem = getResources().getStringArray(R.array.menu_items)[i];
            mRestList= (ListView)rootView.findViewById(R.id.rest_list);
            mRestList.setAdapter(new ArrayAdapter<String>(getActivity(),
                    R.layout.list_item_layout, mPlanetTitles));
            getActivity().setTitle(menuItem);
            return rootView;
        }


    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = getResources().getStringArray(R.array.planets_array)[i];

            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                    "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(planet);
            return rootView;
        }
    }
}






