package Adapter;

import android.os.Parcel;
import android.os.Parcelable;

public class Weight implements Parcelable {
    private int ID;
    private int weight;
    private String weightUnit;
    private int pieces;

    public Weight(int ID, int weight, String weightUnit, int pieces){
        this.ID = ID;
        this.weight = weight;
        this.weightUnit = weightUnit;
        this.pieces = pieces;
    }

    //GETTER
    public int GetID() { return ID; }
    public int GetWeight() { return weight; }
    public String  GetWeightUnit() { return weightUnit; }
    public int GetPieces() { return pieces; }

    //make class baggage parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeInt(weight);
        dest.writeString(weightUnit);
        dest.writeInt(pieces);
    }

    //Creator of class baggage
    protected Weight(Parcel source){
        ID = source.readInt();
        weight = source.readInt();
        weightUnit = source.readString();
        pieces = source.readInt();
    }

    public static final Creator<Weight> CREATOR = new Creator<Weight>() {
        @Override
        public Weight createFromParcel(Parcel source) {
            return new Weight(source);
        }

        @Override
        public Weight[] newArray(int size) {
            return new Weight[size];
        }
    };

}
