package com.example.hostelaid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PreviousClothesDonationAdapter extends RecyclerView.Adapter<PreviousClothesDonationAdapter.ViewHolder> {
    private List<ClothesDonation> donations;

    public PreviousClothesDonationAdapter(List<ClothesDonation> donations) {
        this.donations = donations;
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
        ClothesDonation donation = donations.get(position);
        holder.tvHostel.setText("Hostel: " + donation.getHostel());
        holder.tvTimestamp.setText("Date: " + donation.getTimestamp());
        
        StringBuilder itemsText = new StringBuilder();
        for (ClothItem item : donation.getItems()) {
            itemsText.append("• ").append(item.getClothType())
                    .append(" (").append(item.getSize()).append(") - Qty: ")
                    .append(item.getQuantity()).append("\n");
        }
        holder.tvItems.setText(itemsText.toString().trim());
    }

    @Override
    public int getItemCount() {
        return donations.size();
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

