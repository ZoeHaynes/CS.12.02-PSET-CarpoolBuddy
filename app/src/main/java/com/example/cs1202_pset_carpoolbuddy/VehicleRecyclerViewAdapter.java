package com.example.cs1202_pset_carpoolbuddy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs1202_pset_carpoolbuddy.Vehicles.Vehicle;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class VehicleRecyclerViewAdapter extends RecyclerView.Adapter<VehicleRecyclerViewAdapter.VehicleViewHolder> {
    private Context context;
    private List<Vehicle> vehiclesList;
    private String uid;

    public VehicleRecyclerViewAdapter(Context context, List<Vehicle> vehiclesList,String uid) {
        this.context = context;
        this.vehiclesList = vehiclesList;
        this.uid = uid;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        Vehicle vehicle = vehiclesList.get(position);
        holder.bind(vehicle);
    }

    @Override
    public int getItemCount() {
        return vehiclesList.size();
    }

    public class VehicleViewHolder extends RecyclerView.ViewHolder {
        private TextView ownerTextView;
        private TextView remainingSeatsTextView;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            ownerTextView = itemView.findViewById(R.id.vehicleOwner);
            remainingSeatsTextView = itemView.findViewById(R.id.availSeats);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Vehicle clickedVehicle = vehiclesList.get(position);
                        String vID = clickedVehicle.getVehicleID();
                        Intent intent = new Intent(context, VehicleProfileActivity.class);
                        intent.putExtra("vehicle", vID);
                        intent.putExtra("uid", uid);
                        context.startActivity(intent);
                    }
                }
            });
        }



        public void bind(Vehicle vehicle) {
            ownerTextView.setText(vehicle.getOwner());
            remainingSeatsTextView.setText(String.valueOf(vehicle.getRemainingSeats()));
        }
    }
}