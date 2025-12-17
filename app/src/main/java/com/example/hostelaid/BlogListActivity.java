package com.example.hostelaid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BlogListActivity extends AppCompatActivity {
    private RecyclerView rvBlogs;
    private BlogAdapter adapter;
    private List<Blog> blogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        rvBlogs = findViewById(R.id.rvBlogs);

        // Initialize blogs
        blogs = new ArrayList<>();
        blogs.add(new Blog(1, "5 Simple Ways to Reduce Your Carbon Footprint"));
        blogs.add(new Blog(2, "The Importance of Sustainable Living"));
        blogs.add(new Blog(3, "Zero Waste Lifestyle: A Beginner's Guide"));

        adapter = new BlogAdapter(blogs, blogNumber -> {
            Intent intent = new Intent(BlogListActivity.this, BlogDetailActivity.class);
            intent.putExtra("blog_number", blogNumber);
            startActivity(intent);
        });

        rvBlogs.setLayoutManager(new LinearLayoutManager(this));
        rvBlogs.setAdapter(adapter);
    }

    public static class Blog {
        private int number;
        private String title;

        public Blog(int number, String title) {
            this.number = number;
            this.title = title;
        }

        public int getNumber() { return number; }
        public String getTitle() { return title; }
    }
}

