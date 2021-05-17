package Adapter;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


//flight class holds flight information received from the page
//each flight has been connect with baggage and cargo information
public class Flight implements Parcelable {

    private int flightID;
    private int flightNumber;
    private String departureAirportIATACode;
    private String arrivalAirportIATACode;
    private String departureDate;

    private ArrayList<Baggage> BaggageList;
    private ArrayList<Cargo> CargoList;

    public Flight(int flightID, int flightNumber, String departureAirportIATACode, String arrivalAirportIATACode, String departureDate){
        this.flightID = flightID;
        this.flightNumber = flightNumber;
        this.departureAirportIATACode = departureAirportIATACode;
        this.arrivalAirportIATACode = arrivalAirportIATACode;
        this.departureDate = departureDate;
    }

    //create creator of flight class
    protected Flight(Parcel in) {
        flightID = in.readInt();
        flightNumber = in.readInt();
        departureAirportIATACode = in.readString();
        arrivalAirportIATACode = in.readString();
        departureDate = in.readString();
        BaggageList = in.createTypedArrayList(Baggage.CREATOR);
        CargoList = in.createTypedArrayList(Cargo.CREATOR);
    }

    public static final Creator<Flight> CREATOR = new Creator<Flight>() {
        @Override
        public Flight createFromParcel(Parcel in) {
            return new Flight(in);
        }

        @Override
        public Flight[] newArray(int size) {
            return new Flight[size];
        }
    };

    //GETTER
    public int GetFlightId() { return flightID; }
    public int GetFlightNumber() { return flightNumber; }
    public String GetDepartureAirportIATACode() { return departureAirportIATACode; }
    public String GetArrivalAirportIATACode() { return arrivalAirportIATACode; }
    public String GetDepartureDate() { return departureDate; }
    public ArrayList<Baggage> GetBaggageList() { return BaggageList; }
    public ArrayList<Cargo>  GetCargoList() { return CargoList; }

    //SETTER
    public void SetBaggageList(ArrayList<Baggage> baggages) { BaggageList = baggages; }
    public void SetCargoList(ArrayList<Cargo> cargos) { CargoList = cargos; }


    //make class parcelable  <- needed to send data to other activity
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(flightID);
        dest.writeInt(flightNumber);
        dest.writeString(departureAirportIATACode);
        dest.writeString(arrivalAirportIATACode);
        dest.writeString(departureDate);
        dest.writeTypedList(BaggageList);
        dest.writeTypedList(CargoList);
    }
}
