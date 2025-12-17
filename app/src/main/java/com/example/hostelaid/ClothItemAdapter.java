package com.example.hostelaid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ClothItemAdapter extends RecyclerView.Adapter<ClothItemAdapter.ClothItemViewHolder> {
    private List<ClothItem> clothItems;
    private OnItemDeleteListener deleteListener;

    public interface OnItemDeleteListener {
        void onDelete(int position);
    }

    public ClothItemAdapter(List<ClothItem> clothItems, OnItemDeleteListener deleteListener) {
        this.clothItems = clothItems;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ClothItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cloth_donation, parent, false);
        return new ClothItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClothItemViewHolder holder, int position) {
        ClothItem item = clothItems.get(position);
        holder.tvClothType.setText(item.getClothType());
        holder.tvSize.setText(item.getSize());
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
        
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clothItems.size();
    }

    static class ClothItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvClothType, tvSize, tvQuantity;
        ImageButton btnDelete;

        ClothItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClothType = itemView.findViewById(R.id.tvClothType);
            tvSize = itemView.findViewById(R.id.tvSize);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
} 