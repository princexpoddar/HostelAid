package com.example.hostelaid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {
    private List<BlogListActivity.Blog> blogs;
    private OnBlogClickListener listener;

    public interface OnBlogClickListener {
        void onBlogClick(int blogNumber);
    }

    public BlogAdapter(List<BlogListActivity.Blog> blogs, OnBlogClickListener listener) {
        this.blogs = blogs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_blog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BlogListActivity.Blog blog = blogs.get(position);
        holder.tvTitle.setText(blog.getTitle());
        holder.itemView.setOnClickListener(v -> listener.onBlogClick(blog.getNumber()));
    }

    @Override
    public int getItemCount() {
        return blogs.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvBlogTitle);
        }
    }
}

