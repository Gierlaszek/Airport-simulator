package com.Airport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;

import Adapter.Weight;
import Adapter.Flight;

public class IATA extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner DateSpinner;
    private Spinner RadioButtonChooseSpinner;
    private TextView text0;
    private TextView text1;
    private boolean checkSpinner = true;
    private int choosedRadioButton;
    private ArrayList<Flight> flightList = new ArrayList<>();

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

        //receiving data from the previous activity
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        flightList = args.getParcelableArrayList("flight list");
        text0 = findViewById(R.id.textView3);
        text1 = findViewById(R.id.textView2);

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
                if(event.getAction() == MotionEvent.ACTION_UP){
                    checkSpinner = false;
                    return false;
                }
                return false;
            }
        });

        DateSpinner = findViewById(R.id.DateSpinner);
        DateSpinner.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    checkSpinner = true;
                    return false;
                }
                return false;
            }
        });


        //SPINNER with date
        ArrayList<String> DateFromJSON = new ArrayList<>();
        for(int i = 0; i < flightList.size(); i++){
            DateFromJSON.add(flightList.get(i).GetDepartureDate());
        }
        SetAdapter(DateSpinner, DateFromJSON);
    }

    //radio button operation
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        checkSpinner = false;   //change flag to choose correct spinner
        switch (view.getId()){
            case R.id.RadioButtonFlightNumber:
                if(checked){
                    SetFlightNumberSpinner();
                    choosedRadioButton = 0;
                    findViewById(R.id.tableLayoutNumber).setVisibility(view.VISIBLE);
                    findViewById(R.id.tableLayoutIATA).setVisibility(view.INVISIBLE);
                }
                break;
            case R.id.RadioButtonIATACode:
                if(checked){
                    SetIATACodeSpinner();
                    choosedRadioButton = 1;
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
            text0.setText(item);
        }
        else {
            text1.setText(item);
        }
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
//        FlightNumSpinner = findViewById(R.id.RadioButtonSpinner);
        for(int i = 0; i < flightList.size(); i++){
            FlightNumberFromJSON.add(String.valueOf(flightList.get(i).GetFlightNumber()));
        }
        SetAdapter(RadioButtonChooseSpinner, FlightNumberFromJSON);
    }

    //SPINNER with IATA code
    public void SetIATACodeSpinner(){
        ArrayList<String> IATACodeFromJSON = new ArrayList<>();
//        IATASpinner = findViewById(R.id.RadioButtonSpinner);
        for(int i = 0; i < flightList.size(); i++){
            IATACodeFromJSON.add(flightList.get(i).GetDepartureAirportIATACode());
        }
        for(int i = 0; i < flightList.size(); i++){
            if(!IATACodeFromJSON.contains(flightList.get(i).GetArrivalAirportIATACode())){
                IATACodeFromJSON.add(flightList.get(i).GetArrivalAirportIATACode());
            }
        }
        SetAdapter(RadioButtonChooseSpinner, IATACodeFromJSON);
    }


    //TODO zrobić zeby po kliknieciu pojawila sie tabela dopiero
    public void onClickButton(View view){
        String textFromSpinner = text1.getText().toString();
        if(choosedRadioButton == 0){ //flight number is choosed
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
        else{ //IATA code is choosed
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