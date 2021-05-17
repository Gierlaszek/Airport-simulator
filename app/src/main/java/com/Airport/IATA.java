package com.Airport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import Adapter.Flight;

public class IATA extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner DateSpinner;
    private Spinner IATASpinner;
    private Spinner FlightNumSpinner;
    private TextView text0;
    private TextView text1;
    private TextView text2;
    private ArrayList<Flight> flightList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_a_t);

        //receiving data from the previous activity
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        flightList = args.getParcelableArrayList("flight list");
        text0 = findViewById(R.id.textView3);
        text1 = findViewById(R.id.textView2);


        //SPINNER with date
        ArrayList<String> DateFromJSON = new ArrayList<>();
        DateSpinner = findViewById(R.id.DateSpinner);
        for(int i = 0; i < flightList.size(); i++){
            DateFromJSON.add(flightList.get(i).GetDepartureDate());
        }
        SetAdapter(DateSpinner, DateFromJSON);

    }

    //radio button operation
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.RadioButtonFlightNumber:
                if(checked){
                    SetFlightNumberSpinner();
                }
                break;
            case R.id.RadioButtonIATACode:
                if(checked){
                    SetIATACodeSpinner();
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        text0.setText(item);
//        Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    //Adapter to spinner
    public void SetAdapter(Spinner spinner, ArrayList<String> arrayListWithItemFromJSON){
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,  arrayListWithItemFromJSON);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    //SPINNER with flight number
    public void SetFlightNumberSpinner(){
        ArrayList<String> FlightNumberFromJSON = new ArrayList<>();
        FlightNumSpinner = findViewById(R.id.RadioButtonSpinner);
        for(int i = 0; i < flightList.size(); i++){
            FlightNumberFromJSON.add(String.valueOf(flightList.get(i).GetFlightNumber()));
        }
        SetAdapter(FlightNumSpinner, FlightNumberFromJSON);
    }

    //SPINNER with IATA code
    public void SetIATACodeSpinner(){
        ArrayList<String> IATACodeFromJSON = new ArrayList<>();
        IATASpinner = findViewById(R.id.RadioButtonSpinner);
        for(int i = 0; i < flightList.size(); i++){
            IATACodeFromJSON.add(flightList.get(i).GetDepartureAirportIATACode());
        }
        for(int i = 0; i < flightList.size(); i++){
            if(!IATACodeFromJSON.contains(flightList.get(i).GetArrivalAirportIATACode())){
                IATACodeFromJSON.add(flightList.get(i).GetArrivalAirportIATACode());
            }
        }
        SetAdapter(IATASpinner, IATACodeFromJSON);
    }
}