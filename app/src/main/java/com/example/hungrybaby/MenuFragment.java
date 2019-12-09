package com.example.hungrybaby;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hungrybaby.Model.Item;
import com.example.hungrybaby.ViewHolder.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuFragment extends Fragment {


//    RecyclerView recyclerView;
//    DatabaseReference databaseMenu;

//    ArrayList<Item> itemArrayList;
//    DatabaseReference databaseMenu;

//    FirebaseAuth myAuth;

//    String currentUserId;

//    ArrayList<Item> items;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        return view;
    }

//        recyclerView = view.findViewById(R.id.recycler);
//        MyAdapter myAdapter = new MyAdapter(this, itemArrayList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


//        recyclerView.setAdapter(new RecyclerViewAdapter());

//        myAuth = FirebaseAuth.getInstance();
//        currentUserId = myAuth.getCurrentUser().getUid();
//
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("items");


//
//    public static class ItemsHolder extends RecyclerView.ViewHolder{
//
//        CardView mCardView;
//        TextView  name,price;
//        ImageView image;
//        public ItemsHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//
//        public ItemsHolder(LayoutInflater inflater, ViewGroup container){
//            super(inflater.inflate(R.layout.layout_item,container,false));
//
//            mCardView = itemView.findViewById(R.id.card_container);
//            name = itemView.findViewById(R.id.itemName);
//            price = itemView.findViewById(R.id.itemPrice);
//            image = itemView.findViewById(R.id.itemImage);
//
//            mCardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mCardView.getContext(), foodActivity.class);
//                    intent.putExtra("NAME", name.getText().toString());
//                    intent.putExtra("PRICE", price.getText().toString());
//                    mCardView.getContext().startActivity(intent);
//                }
//            });
//        }
//    }
//
//    private class RecyclerViewAdapter extends RecyclerView.Adapter<ItemsHolder>{
//
//        @NonNull
//        @Override
//        public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//            LayoutInflater inflater = LayoutInflater.from(getActivity());
//            return new ItemsHolder(inflater, parent);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull final ItemsHolder holder, int position) {
//
////            holder.name.setText("Wings");
////            holder.price.setText("300");
////            databaseMenu = FirebaseDatabase.getInstance().getReference("items");
//
//            databaseMenu.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                        holder.name.setText(dataSnapshot1.child("itemName").getValue(String.class));
//                        holder.price.setText(Integer.toString(dataSnapshot1.child("price").getValue(Integer.class)));
////                        item.setImage(dataSnapshot1.child("image").getValue().toString());
////                        items.add(item);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return 1;
//        }
//    }
}

