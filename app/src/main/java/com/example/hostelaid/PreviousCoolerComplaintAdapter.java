package com.example.hostelaid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PreviousCoolerComplaintAdapter extends RecyclerView.Adapter<PreviousCoolerComplaintAdapter.ViewHolder> {
    private List<CoolerComplaint> complaints;

    public PreviousCoolerComplaintAdapter(List<CoolerComplaint> complaints) {
        this.complaints = complaints;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_previous_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CoolerComplaint complaint = complaints.get(position);
        holder.tvHostel.setText("Hostel: " + complaint.getHostel());
        holder.tvTimestamp.setText("Date: " + complaint.getTimestamp());
        
        String details = "Floor: " + complaint.getFloor() + "\n" +
                        "Cooler Number: " + complaint.getCoolerNumber() + "\n" +
                        "Problem: " + complaint.getProblem();
        holder.tvItems.setText(details);
    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHostel, tvTimestamp, tvItems;

        ViewHolder(View itemView) {
            super(itemView);
            tvHostel = itemView.findViewById(R.id.tvHostel);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvItems = itemView.findViewById(R.id.tvItems);
        }
    }
}

