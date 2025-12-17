package com.example.hostelaid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BlogDetailActivity extends AppCompatActivity {
    private TextView tvBlogTitle, tvBlogContent;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);

        // Initialize views
        tvBlogTitle = findViewById(R.id.tvBlogTitle);
        tvBlogContent = findViewById(R.id.tvBlogContent);
        btnBack = findViewById(R.id.btnBack);

        // Get blog number from intent
        int blogNumber = getIntent().getIntExtra("blog_number", 1);

        // Set blog content based on number
        switch (blogNumber) {
            case 1:
                tvBlogTitle.setText("5 Simple Ways to Reduce Your Carbon Footprint");
                tvBlogContent.setText("1. Use Public Transportation or Carpool\n\n" +
                    "One of the most effective ways to reduce your carbon footprint is to minimize your use of personal vehicles. Public transportation, carpooling, biking, or walking can significantly reduce greenhouse gas emissions.\n\n" +
                    "2. Reduce Meat Consumption\n\n" +
                    "The meat industry is a major contributor to greenhouse gas emissions. Try incorporating more plant-based meals into your diet. Even one meatless day per week can make a difference.\n\n" +
                    "3. Conserve Energy at Home\n\n" +
                    "Simple changes like using LED light bulbs, unplugging electronics when not in use, and using energy-efficient appliances can reduce your energy consumption.\n\n" +
                    "4. Reduce, Reuse, Recycle\n\n" +
                    "Follow the three R's to minimize waste. Buy products with minimal packaging, reuse items when possible, and recycle materials properly.\n\n" +
                    "5. Support Renewable Energy\n\n" +
                    "If possible, switch to a renewable energy provider or consider installing solar panels. Every bit of clean energy helps reduce our reliance on fossil fuels.");
                break;
            case 2:
                tvBlogTitle.setText("The Importance of Sustainable Living");
                tvBlogContent.setText("Sustainable living is crucial for the future of our planet. Here's why:\n\n" +
                    "1. Preserving Natural Resources\n\n" +
                    "Our planet's resources are finite. By living sustainably, we ensure that future generations will have access to clean water, fresh air, and natural resources.\n\n" +
                    "2. Reducing Climate Change\n\n" +
                    "Sustainable practices help reduce greenhouse gas emissions, which are the primary cause of climate change. Small changes in our daily lives can have a big impact.\n\n" +
                    "3. Protecting Biodiversity\n\n" +
                    "Sustainable living helps protect ecosystems and wildlife. By reducing our environmental impact, we help maintain the delicate balance of nature.\n\n" +
                    "4. Improving Quality of Life\n\n" +
                    "Sustainable living often leads to healthier lifestyles. Eating local, organic food, using non-toxic products, and spending time in nature all contribute to better health.\n\n" +
                    "5. Economic Benefits\n\n" +
                    "Sustainable practices can save money in the long run. Energy-efficient appliances, water conservation, and waste reduction all lead to lower utility bills.");
                break;
            case 3:
                tvBlogTitle.setText("Zero Waste Lifestyle: A Beginner's Guide");
                tvBlogContent.setText("Starting a zero waste lifestyle can seem overwhelming, but it's all about taking small steps. Here's how to begin:\n\n" +
                    "1. Start with the 5 R's\n\n" +
                    "Refuse what you don't need, Reduce what you do need, Reuse what you can, Recycle what you can't refuse, reduce, or reuse, and Rot (compost) the rest.\n\n" +
                    "2. Bring Your Own\n\n" +
                    "Always carry reusable items: water bottle, coffee cup, shopping bags, and utensils. This simple habit can significantly reduce single-use waste.\n\n" +
                    "3. Shop Smart\n\n" +
                    "Buy in bulk when possible, choose products with minimal packaging, and support local farmers' markets. Bring your own containers for bulk items.\n\n" +
                    "4. Make Your Own\n\n" +
                    "Many household products can be made at home using simple ingredients. Try making your own cleaning products, personal care items, or even food items.\n\n" +
                    "5. Compost\n\n" +
                    "Start composting your food scraps and yard waste. This reduces landfill waste and creates nutrient-rich soil for your garden.\n\n" +
                    "Remember, zero waste is a journey, not a destination. Every small change makes a difference!");
                break;
        }

        // Set up back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
} 