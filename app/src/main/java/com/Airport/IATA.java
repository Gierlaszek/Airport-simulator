package com.Airport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import Adapter.Flight;

public class IATA extends AppCompatActivity {

    private Spinner DataSpinner;
    private Spinner IATASpinner;
    private Spinner FlightNumSpinner;
    private ArrayList<Flight> flightList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_a_t);

        //receiving data from the previous activity
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        flightList = args.getParcelableArrayList("flight list");

        List<String> DataFromJSON = new ArrayList<>();


        DataSpinner = findViewById(R.id.DataSpinner);
//        DataSpinner.add



    }
}