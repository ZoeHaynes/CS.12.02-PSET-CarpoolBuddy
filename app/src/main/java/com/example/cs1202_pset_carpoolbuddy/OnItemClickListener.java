package com.example.cs1202_pset_carpoolbuddy;

import com.example.cs1202_pset_carpoolbuddy.Vehicles.Vehicle;

/**
 * This interface is for the OnItemClickListener, used in the RecyclerView.
 *
 * @author Zoe Haynes
 * @version 0.1
 */
public interface OnItemClickListener {
    void onItemClick(int position, Vehicle vehicle);
}
