package com.example.hungrybaby.ViewHolder;

import android.content.Context;
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

import org.w3c.dom.Text;

public class ItemViewHolder extends RecyclerView.ViewHolder
{
    TextView name,price;
    ImageView image;
    Button btn;

    Context context;
    public ItemViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.itemName);
        price = (TextView) itemView.findViewById(R.id.itemPrice);
        image = (ImageView) itemView.findViewById(R.id.itemImage);
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
