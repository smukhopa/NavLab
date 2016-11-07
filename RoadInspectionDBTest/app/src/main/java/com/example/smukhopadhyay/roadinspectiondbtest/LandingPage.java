package com.example.smukhopadhyay.roadinspectiondbtest;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smukhopadhyay.database.DatabaseHelper;

import java.util.ArrayList;

public class LandingPage extends AppCompatActivity {

    private Button citySearch;
    private EditText cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cityName = (EditText)findViewById(R.id.city_name);
        citySearch = (Button)findViewById(R.id.city_search);
        insertCity("Pittsburgh", "0", "40.4385", "40.4301", "-79.9217", "-79.9322");
    }

    // Invoked when search city is clicked
    public void citySearchButtonClick(View view) {
        String cityNameString = cityName.getText().toString();
        if (cityNameString.equals("")) {
            Toast.makeText(this,"Please enter a city", Toast.LENGTH_SHORT).show();
        } else {
            // Search through DB and find city
            ArrayList<String> res = searchCityTable(cityNameString);
            if (res == null) {
                Toast.makeText(this,"City not found", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"City found!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private ArrayList<String> searchCityTable(String cityNameString) {
        DatabaseHelper helper = new DatabaseHelper(this);
        ArrayList<String> res = helper.getCityData(cityNameString);
        return res;
    }

    private void insertCity(String cityName, String deviation, String latMax, String latMin, String lngMax, String lngMin) {
        DatabaseHelper helper = new DatabaseHelper(this);
        boolean res = helper.insertCityTableData(cityName, deviation, latMax, latMin, lngMax, lngMin);
        Toast.makeText(this, Boolean.toString(res), Toast.LENGTH_SHORT).show();
    }


}
