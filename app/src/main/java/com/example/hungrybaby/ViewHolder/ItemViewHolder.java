package com.example.hungrybaby.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hungrybaby.Interface.ItemClickListener;
import com.example.hungrybaby.R;
import com.example.hungrybaby.foodActivity;

import org.w3c.dom.Text;

public class ItemViewHolder extends RecyclerView.ViewHolder
{
    public TextView name,price;
    public ImageView image;
    public View v;
    Button btn;


    Context context;
    public ItemViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.itemName);
        price = (TextView) itemView.findViewById(R.id.price);
        image = (ImageView) itemView.findViewById(R.id.itemImage);

        v = itemView;
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), foodActivity.class);
                    intent.putExtra("NAME", name.getText().toString());
                    intent.putExtra("PRICE", price.getText().toString());
                    v.getContext().startActivity(intent);
            }
        });
    }

    public void onClick(final int position)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
