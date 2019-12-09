package com.example.hungrybaby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hungrybaby.Model.Item;
import com.example.hungrybaby.ViewHolder.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class menuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseMenu;
    private FirebaseRecyclerOptions<Item> items;
    private FirebaseRecyclerAdapter<Item, ItemViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        databaseMenu = FirebaseDatabase.getInstance().getReference("items");

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        items = new FirebaseRecyclerOptions.Builder<Item>().setQuery(databaseMenu, Item.class).build();

        adapter = new FirebaseRecyclerAdapter<Item, ItemViewHolder>(items) {
            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i, @NonNull Item item) {
                Picasso.get().load(item.getImage()).into(itemViewHolder.image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                itemViewHolder.price.setText(item.getPrice());
                itemViewHolder.name.setText(item.getItemName());
                itemViewHolder.setImageLink(item.getImage());

            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_items_layout, parent, false);
                return new ItemViewHolder(view);
            }
        };

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
    }

    @Override
    protected void onStop(){
        if(adapter!=null)
            adapter.stopListening();
        super.onStop();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(adapter!=null)
            adapter.startListening();
    }

    public void viewProfile(View v){
        Intent intent = new Intent(menuActivity.this, profileActivity.class);
        startActivity(intent);
    }
}