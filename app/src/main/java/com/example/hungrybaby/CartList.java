package com.example.hungrybaby;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hungrybaby.Model.Cart;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CartList extends ArrayAdapter<Cart> {
    private Activity context;
    List<Cart> carts;

    public CartList(Activity context, List<Cart> cart){
        super(context, R.layout.cart_list,cart);
        this.context = context;
        this.carts = cart;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewCart = inflater.inflate(R.layout.cart_list,null,true);

        TextView cartName = (TextView) listViewCart.findViewById(R.id.itemName);
        TextView cartPrice = (TextView) listViewCart.findViewById(R.id.itemPrice);
        TextView cartQty = (TextView) listViewCart.findViewById(R.id.itemQty);

        Cart cart = carts.get(position);
        cartName.setText(cart.getOrder());
        cartPrice.setText(cart.getCost());
        cartQty.setText(Integer.toString(cart.getQuantity()));

        return listViewCart;

    }
}
