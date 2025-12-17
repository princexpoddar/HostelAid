package com.example.hostelaid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PreviousFoodWasteAdapter extends RecyclerView.Adapter<PreviousFoodWasteAdapter.ViewHolder> {
    private List<FoodWasteRequest> requests;

    public PreviousFoodWasteAdapter(List<FoodWasteRequest> requests) {
        this.requests = requests;
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
        FoodWasteRequest request = requests.get(position);
        holder.tvHostel.setText("Hostel: " + request.getHostel());
        holder.tvTimestamp.setText("Date: " + request.getTimestamp());
        
        StringBuilder itemsText = new StringBuilder();
        for (FoodItem item : request.getItems()) {
            itemsText.append("• ").append(item.getItemName())
                    .append(" (").append(item.getQuantity()).append(" kg)\n");
        }
        holder.tvItems.setText(itemsText.toString().trim());
    }

    @Override
    public int getItemCount() {
        return requests.size();
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

