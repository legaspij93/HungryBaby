package com.example.hungrybaby;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hungrybaby.Model.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MenuFragment extends Fragment {


    RecyclerView recyclerView;

//    ArrayList<Item> itemArrayList;
    DatabaseReference databaseMenu;

    FirebaseAuth myAuth;

    String currentUserId;

    ArrayList<Item> items = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_menu, container, false);
//
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
//        MyAdapter myAdapter = new MyAdapter(this, itemArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerViewAdapter());

//        myAuth = FirebaseAuth.getInstance();
//        currentUserId = myAuth.getCurrentUser().getUid();
//
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("items");

        return view;
    }


    public static class ItemsHolder extends RecyclerView.ViewHolder{

        CardView mCardView;
        TextView  name,price;
        ImageView image;
        public ItemsHolder(@NonNull View itemView) {
            super(itemView);
        }

        public ItemsHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.layout_item,container,false));

            mCardView = itemView.findViewById(R.id.card_container);
            name = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.itemPrice);
            image = itemView.findViewById(R.id.itemImage);

            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mCardView.getContext(), foodActivity.class);
                    intent.putExtra("NAME", name.getText().toString());
                    intent.putExtra("PRICE", price.getText().toString());
                    mCardView.getContext().startActivity(intent);
                }
            });
        }
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<ItemsHolder>{

        @NonNull
        @Override
        public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new ItemsHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemsHolder holder, int position) {

            holder.name.setText("Wings");
            holder.price.setText("300");
//            databaseMenu = FirebaseDatabase.getInstance().getReference().child("items");
//
//            databaseMenu.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                        Item item = new Item();
//                        item.setName(dataSnapshot1.child("itemName").getValue().toString());
//                        item.setPrice(dataSnapshot1.child("price").getValue().toString());
//                        item.setImage(dataSnapshot1.child("image").getValue().toString());
//                        items.add(item);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

        }

        @Override
        public int getItemCount() {
            return 1;
        }
    }
}

