package com.example.hostelaid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder> {
    private List<FoodItem> foodItems;
    private OnItemDeleteListener deleteListener;

    public interface OnItemDeleteListener {
        void onDelete(int position);
    }

    public FoodItemAdapter(List<FoodItem> foodItems, OnItemDeleteListener deleteListener) {
        this.foodItems = foodItems;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food_waste, parent, false);
        return new FoodItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position) {
        FoodItem item = foodItems.get(position);
        holder.tvFoodItem.setText(item.getItemName());
        holder.tvQuantity.setText(item.getQuantity() + " kg");
        
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    static class FoodItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodItem, tvQuantity;
        ImageButton btnDelete;

        FoodItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodItem = itemView.findViewById(R.id.tvFoodItem);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
} 