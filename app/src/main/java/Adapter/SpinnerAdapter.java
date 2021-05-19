package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.Airport.R;

import java.util.ArrayList;

public class SpinnerAdapter {
    private static AdapterView.OnItemSelectedListener listener;

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public SpinnerAdapter(AdapterView.OnItemSelectedListener listener_, Context context_){
        listener = listener_;
        context = context_;
    }

    //Adapter to spinner
    public void SetAdapter(Spinner spinner, ArrayList<String> arrayListWithItemFromJSON){
        spinner.setOnItemSelectedListener(listener);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,  arrayListWithItemFromJSON);
        //----------------------------------------------------------------------------------------------------
        // it's a debug message, not error
        // D/OpenGLRenderer: endAllActiveAnimators on 0x7133bf1a60 (DropDownListView) with handle 0x7053c17830
        //----------------------------------------------------------------------------------------------------
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    //SPINNER with flight number
    public void SetFlightNumberSpinner(Spinner spinner, ArrayList<Flight> flights){
        ArrayList<String> FlightNumberFromJSON = new ArrayList<>();
        FlightNumberFromJSON.add("NONE");
        for(int i = 0; i < flights.size(); i++){
            FlightNumberFromJSON.add(String.valueOf(flights.get(i).GetFlightNumber()));
        }
        spinner.setPopupBackgroundResource(R.drawable.flightnumback);
        SetAdapter(spinner, FlightNumberFromJSON);
    }

    //SPINNER with IATA code
    public void SetIATACodeSpinner(Spinner spinner, ArrayList<Flight> flights){
        ArrayList<String> AllIATACodeFromJSON = new ArrayList<>();
        AllIATACodeFromJSON.add("NONE");
        for(int i = 0; i < flights.size(); i++){
            if(!AllIATACodeFromJSON.contains(flights.get(i).GetDepartureAirportIATACode())){
                AllIATACodeFromJSON.add(flights.get(i).GetDepartureAirportIATACode());
            }
            if(!AllIATACodeFromJSON.contains(flights.get(i).GetArrivalAirportIATACode())){
                AllIATACodeFromJSON.add(flights.get(i).GetArrivalAirportIATACode());
            }
        }
        spinner.setPopupBackgroundResource(R.drawable.iatabackl);
        SetAdapter(spinner, AllIATACodeFromJSON);
    }

}
