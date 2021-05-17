package Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.Airport.R;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolder>{


    private ArrayList<Flight> flightArrayList = new ArrayList<>();

    public FlightAdapter(ArrayList<Flight> flightArrayList) { this.flightArrayList = flightArrayList; }

    @NonNull
    @Override
    public FlightAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View list = layoutInflater.inflate(R.layout.activity_i_a_t, parent, false);
        ViewHolder viewHolder = new ViewHolder(list);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FlightAdapter.ViewHolder holder, int position) {
//        holder.FlightNumberText.setText(flightArrayList.get(position).GetFlightNumber());
//        holder.IATAText.setText(flightArrayList.get(position).);
    }

    @Override
    public int getItemCount() {
        return flightArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView FlightNumberText;
        public TextView IATAText;
        public TextView DateText;
//        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
//            this.FlightNumberText = (TextView) itemView.findViewById(R.id.FlightNumberText);
//            this.IATAText = (TextView) itemView.findViewById(R.id.IATAText);
//            this.DateText = (TextView) itemView.findViewById(R.id.DateText);
//            this.relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }
}
