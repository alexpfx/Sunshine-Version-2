package com.example.android.sunshine.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment implements FetchWeatherTask.Callback {
    private ListView listView;

    private static final String TAG = "ForecastFragment";
    private ArrayAdapter<String> forecastAdapter;

    public ForecastFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        forecastAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, new ArrayList());
        listView.setAdapter(
                forecastAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String forecast = forecastAdapter.getItem(i);
                Intent detail = new Intent(getActivity(), DetailActivity.class).putExtra(Intent.EXTRA_TEXT, forecast);

                startActivity(detail);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_refresh:
                return updateWeather();
            case R.id.action_choose_pref_location:
                return showPrefLocationOnMap();
        }
        return false;
    }

    private boolean showPrefLocationOnMap() {

        SharedPreferences sharedPreferences = AppUtils.getSharedPreferences(getActivity());
        String zip = sharedPreferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_default_location_code));
        Uri q = Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q", zip).build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(q);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }else{
            Log.d(TAG, "showPrefLocationOnMap: cannot handle map");
        }


        return true;
    }

    private boolean updateWeather() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.pref_key), Context.MODE_PRIVATE);
        String locationCode = sharedPreferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_default_location_code));
        String unit = sharedPreferences.getString(getString(R.string.pref_temperature_units_key), getString(R.string.pref_temperatura_units_default_value));
        new FetchWeatherTask(this).execute(locationCode, unit);
        return true;
    }

    @Override
    public void onDataReceived(String data[]) {
        forecastAdapter.clear();
        for (String s : data) {
            forecastAdapter.add(s);
        }

    }


}




