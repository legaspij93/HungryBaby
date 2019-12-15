package com.example.hungrybaby;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hungrybaby.Model.Cart;
import com.example.hungrybaby.Model.Order;

import java.util.List;

public class OrderList extends ArrayAdapter<Order> {

    private Activity context;
    List<Order> orders;

    public OrderList(Activity context, List<Order> orders){
        super(context, R.layout.order_list, orders);
        this.context = context;
        this.orders = orders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewOrder = inflater.inflate(R.layout.order_list,null,true);

        TextView orderNumber = (TextView) listViewOrder.findViewById(R.id.orderNumber);
        TextView dateOrdered = (TextView) listViewOrder.findViewById(R.id.dateOrdered);

        Order order = orders.get(position);
        orderNumber.setText(order.getOrderId());
        dateOrdered.setText(order.getDateOrdered());

        return listViewOrder;

    }
}
