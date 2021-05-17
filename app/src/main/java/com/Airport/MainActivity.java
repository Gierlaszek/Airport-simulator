package com.Airport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapter.Baggage;
import Adapter.Cargo;
import Adapter.Flight;


public class MainActivity extends AppCompatActivity {

    private TextView res_my_text;
    public ArrayList<Flight> flightList = new ArrayList<Flight>();
    private Button but;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        res_my_text = findViewById(R.id.textView);
        but = findViewById(R.id.button);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url_baggage ="https://www.json-generator.com/api/json/get/cqYnNWHmNu?indent=2";
        String url_flight = "https://www.json-generator.com/api/json/get/bUlKDRPnci?indent=2";

        //TODO w trakcie pobierania wyswietla sie gif, jak sie skonczy to wtedy przechodzi dalej

        // Request a json response from the provided URL
        JsonArrayRequest jsonRequest = new JsonArrayRequest
                (Request.Method.GET, url_flight, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try{
                                //extract single detail about flight from JSONArray
                                //and create Flight list, who has information about ID, number etc.
                                JSONObject flightDetail = response.getJSONObject(i);
                                int flightID = flightDetail.getInt("flightId");
                                int flightNumber = flightDetail.getInt("flightNumber");
                                String departureAirportIATACode = flightDetail.getString("departureAirportIATACode");
                                String arrivalAirportIATACode = flightDetail.getString("arrivalAirportIATACode");
                                String departureDate = flightDetail.getString("departureDate");
                                flightList.add(new Flight(flightID, flightNumber, departureAirportIATACode, arrivalAirportIATACode, departureDate));
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        res_my_text.setText("Something go wrong !!!");
                    }
                });

        JsonArrayRequest jsonRequest2 = new JsonArrayRequest
                (Request.Method.GET, url_baggage, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            ArrayList<Baggage> baggageList = new ArrayList<>();
                            ArrayList<Cargo> cargoList = new ArrayList<>();
                            try{
                                JSONObject baggageDetail = response.getJSONObject(i);
                                int flightID = baggageDetail.getInt("flightId");

                                //need to create a baggage and cargo list for each flight ID and then link them to the correct flight
                                //we get a json Array which consists of 3 things: ID, baggage, cargo
                                JSONArray baggage = baggageDetail.getJSONArray("baggage");
                                JSONArray cargo = baggageDetail.getJSONArray("cargo");

                                //create a list of baggage and cargo
                                for(int j = 0; j < baggage.length(); j++){
                                    JSONObject baggageObject = baggage.getJSONObject(j);
                                    int ID = baggageObject.getInt("id");
                                    int weight = baggageObject.getInt("weight");
                                    String weightUnit = baggageObject.getString("weightUnit");
                                    int pieces = baggageObject.getInt("pieces");
                                    baggageList.add(new Baggage(ID, weight, weightUnit, pieces));
                                }

                                for(int j = 0; j < cargo.length(); j++){
                                    JSONObject cargoObject = cargo.getJSONObject(j);
                                    int ID = cargoObject.getInt("id");
                                    int weight = cargoObject.getInt("weight");
                                    String weightUnit = cargoObject.getString("weightUnit");
                                    int pieces = cargoObject.getInt("pieces");
                                    cargoList.add(new Cargo(ID, weight, weightUnit, pieces));
                                }

                                //connect baggage and cargo list to correct flight
                                for(int n = 0; n < flightList.size(); n++) {
                                    if (flightID == flightList.get(n).GetFlightId()) {
                                        flightList.get(n).SetBaggageList(baggageList);
                                        flightList.get(n).SetCargoList(cargoList);
                                    }
                                }
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        res_my_text.setText("Something go wrong !!!");
                    }
                });
        queue.add(jsonRequest);
        queue.add(jsonRequest2);
    }

    public void onclickButton(View view){
        //TODO przesłać listę do kolejnej strony gdzie zostaną dodane do adaptera
        // jeśli nie uda się przesłać, to zrobić w stronie IATA odbieranie danych,
        // następnie jak będzie gotowy adapter, to wyświetlanie danych w tabeli,  i zrobić kliknięcie na wierszu żeby otworzyło się małe nowe okno które bedzie miało infromacje wszystkie
        // jak się nie uda zrobić kliknięcia to zrobić jakiś mały przycisk w tabeli który pozwoli na kliknięcie
        // jak wszystko będzie okej, to zrobić gifa na stronę poczatkową
        // na samym końcu jak zostanie czas to spróbować zrobić samouczek

        //TODO optymalizacja kodu:
        // - zrobić klasę, która będzie miała queue, i bedzie miala metode JSONArrayRequest
        // - i w niej zrobić GETTER flight list, i tutaj na głównym menu odbierać dane i przesyłać do nowej strony / lub odbierać na nowej stronie

        //send array list with flight object to next activity
        Intent data = new Intent(this, IATA.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("flight list", flightList);
        data.putExtra("bundle", bundle);
        startActivity(data);
    }

}