package com.Airport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;

import Adapter.SpinnerAdapter;
import Adapter.Weight;
import Adapter.Flight;

public class IATA extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner DateSpinner;
    private Button filter;
    private Spinner RadioButtonChooseSpinner;
    private TextView TextForSpinner1;
    private TextView TextForSpinner2;
    private boolean checkSpinner = false;
    private boolean prevoiusValueIsNONE;
    private int choosedRadioButton = 0;
    private ArrayList<Flight> flightList = new ArrayList<>();

    private SpinnerAdapter adapter;


    private TextView row0_numLayout;
    private TextView row1_numLayout;
    private TextView row2_numLayout;

    private TextView row0_IATALayout;
    private TextView row1_IATALayout;
    private TextView row2_IATALayout;
    private TextView row3_IATALayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_a_t);

        filter = findViewById(R.id.Filtr);
        filter.setBackgroundColor(Color.parseColor("#773731"));

        adapter = new SpinnerAdapter(this, this);

        //-----------------------------------------
        //receiving data from the previous activity
        //-----------------------------------------
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        flightList = args.getParcelableArrayList("flight list");

        TextForSpinner1 = findViewById(R.id.textView2);
        TextForSpinner2 = findViewById(R.id.textView3);

        //------------------------------
        //init text view in table layout
        //------------------------------
        row0_numLayout = findViewById(R.id.Detail_Cargo_weig);
        row1_numLayout = findViewById(R.id.Detail_Baggage_weig);
        row2_numLayout = findViewById(R.id.Detail_Total);

        row0_IATALayout = findViewById(R.id.Detail_num_dep);
        row1_IATALayout = findViewById(R.id.Detail_num_arr);
        row2_IATALayout = findViewById(R.id.Detail_bag_dep);
        row3_IATALayout = findViewById(R.id.Detail_bag_arr);

        //------------------------------------
        //change flag to choose correct spinner
        //------------------------------------
        RadioButtonChooseSpinner = findViewById(R.id.RadioButtonSpinner);
        RadioButtonChooseSpinner.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){     //change textview to output
                    checkSpinner = false;
                    return false;  // return true in both case changes the behavior of the spinner
                }
                return false;
            }
        });

        DateSpinner = findViewById(R.id.DateSpinner);
        DateSpinner.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {   //change textview to output
                if(event.getAction() == MotionEvent.ACTION_UP){
                    checkSpinner = true;
                    return false;   // return true in both case changes the behavior of the spinner
                }
                return false;
            }
        });
    }

    //radio button operation
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        checkSpinner = false;   //change flag to choose correct spinner
        switch (view.getId()){
            case R.id.RadioButtonFlightNumber:
                if(checked){
                    adapter.SetFlightNumberSpinner(RadioButtonChooseSpinner, flightList);
                    choosedRadioButton = 1;
                    findViewById(R.id.tableLayoutNumber).setVisibility(view.VISIBLE);
                    findViewById(R.id.tableLayoutIATA).setVisibility(view.INVISIBLE);
                }
                break;
            case R.id.RadioButtonIATACode:
                if(checked){
                    adapter.SetIATACodeSpinner(RadioButtonChooseSpinner, flightList);
                    choosedRadioButton = 2;
                    findViewById(R.id.tableLayoutNumber).setVisibility(view.INVISIBLE);
                    findViewById(R.id.tableLayoutIATA).setVisibility(view.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        if(checkSpinner){
            //------------
            //date spinner
            //------------
            TextForSpinner2.setText(item);  //time  -> if select time -> set correct IATA code or flight number
            for(int i = 0; i < flightList.size(); i++){
                if(flightList.get(i).GetDepartureDate().equals(item)){
                    if(choosedRadioButton == 1){
                        TextForSpinner1.setText(String.valueOf(flightList.get(i).GetFlightNumber()));
                    }
                    else if(choosedRadioButton == 2){
                        if(prevoiusValueIsNONE){
                            TextForSpinner1.setText(String.valueOf(flightList.get(i).GetDepartureAirportIATACode()));
                        }
                    }
                }
            }
            checkSpinner = false;
        }
        else {
            //--------------------------
            //IATA code or flight number
            //--------------------------
            ArrayList<String> DateFromJSON = new ArrayList<>();
            TextForSpinner1.setText(item);
            if(item.equals("NONE")){
                //SPINNER with date
                prevoiusValueIsNONE = true;
                DateFromJSON.add("Choose date");
                for(int i = 0; i < flightList.size(); i++){
                    DateFromJSON.add(flightList.get(i).GetDepartureDate());
                }
                adapter.SetAdapter(DateSpinner, DateFromJSON);
            }
            else{
                //Data spinner has data associated with a specific Flight number or IATA code
                prevoiusValueIsNONE = false;
                for(int i = 0; i < flightList.size(); i++){
                    if(flightList.get(i).GetDepartureAirportIATACode().equals(item)){
                        DateFromJSON.add(flightList.get(i).GetDepartureDate());
                    }
                    else if(flightList.get(i).GetArrivalAirportIATACode().equals(item)) {
                        DateFromJSON.add(flightList.get(i).GetDepartureDate());
                    }

                    if(String.valueOf(flightList.get(i).GetFlightNumber()).equals(item)){
                        DateFromJSON.add(flightList.get(i).GetDepartureDate());
                    }
                }
                adapter.SetAdapter(DateSpinner, DateFromJSON);
            }
            checkSpinner = true;
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    //after press button show correct table layout
    public void onClickButton(View view){
        String textFromSpinner = TextForSpinner1.getText().toString();
        if(choosedRadioButton == 1){ //flight number is choosed
            for(int i = 0; i < flightList.size(); i++){
                if(String.valueOf(flightList.get(i).GetFlightNumber()).equals(textFromSpinner)){
                    int CargoWeight = calculateWeight(flightList.get(i).GetCargoList());
                    int BaggageWeight = calculateWeight(flightList.get(i).GetBaggageList());
                    int TotalWeight = BaggageWeight + CargoWeight;
                    row0_numLayout.setText(String.valueOf(CargoWeight));
                    row1_numLayout.setText(String.valueOf(BaggageWeight));
                    row2_numLayout.setText(String.valueOf(TotalWeight));
                }
            }
        }
        else if(choosedRadioButton == 2){ //IATA code is choosed
            int numOfDepartureFlight = 0;
            int numOfArrivalFlight = 0;
            int numOfArrivalPieces = 0;
            int numOfDeparturePieces = 0;

            for(int i = 0; i < flightList.size(); i++){
                if(flightList.get(i).GetDepartureAirportIATACode().equals(textFromSpinner)){
                    numOfDepartureFlight += 1;
                    numOfDeparturePieces += calculatePieces(flightList.get(i).GetBaggageList());
                }

                if(flightList.get(i).GetArrivalAirportIATACode().equals(textFromSpinner)){
                    numOfArrivalFlight += 1;
                    numOfArrivalPieces += calculatePieces(flightList.get(i).GetBaggageList());
                }
            }
            row0_IATALayout.setText(String.valueOf(numOfDepartureFlight));
            row1_IATALayout.setText(String.valueOf(numOfArrivalFlight));
            row2_IATALayout.setText(String.valueOf(numOfDeparturePieces));
            row3_IATALayout.setText(String.valueOf(numOfArrivalPieces));
        }
    }


    //TODO
    // jeśli zostanie czas to przenieść poniższe metody do klasy weight !!!
    private int calculatePieces(ArrayList<Weight> arrayList){
        int totalePieces = 0;
        for(int i = 0; i < arrayList.size(); i++){
            totalePieces += arrayList.get(i).GetPieces();
        }
        return totalePieces;
    }

    private int calculateWeight(ArrayList<Weight>  arrayList  ){  //type weight -> cargo or baggage
        int totalWeight = 0;
        for(int i = 0; i < arrayList.size(); i++){
            if(arrayList.get(i).GetWeightUnit().equals("kg")){
                totalWeight += arrayList.get(i).GetWeight();
            }
            else{
                totalWeight += arrayList.get(i).GetWeight() * 0.453;  //convert lb -> kg
            }
        }
        return totalWeight;
    }
}