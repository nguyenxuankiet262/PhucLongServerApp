package com.phonglongapp.xk.phuclongserverapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.phonglongapp.xk.phuclongserverapp.Common.Common;
import com.phonglongapp.xk.phuclongserverapp.Interface.ItemClickListener;
import com.phonglongapp.xk.phuclongserverapp.Interface.ItemLongClickListener;
import com.phonglongapp.xk.phuclongserverapp.Model.Order;
import com.phonglongapp.xk.phuclongserverapp.R;
import com.phonglongapp.xk.phuclongserverapp.ViewHolder.OrderViewHolder;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    Context context;
    List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.order_item_layout,parent,false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.time_order.setText(Common.getTimeAgo(Long.parseLong(orderList.get(position).getId()),context));
        holder.id_order.setText("#"+orderList.get(position).getId());
        if(orderList.get(position).getStatus().equals("Ordered")){
            holder.status_order.setText("New Order");
        }
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(context,orderList.get(position).getName()
                        +"\n"+ orderList.get(position).getPrice()
                +"\n"+orderList.get(position).getCartList().get(0).getcName(),Toast.LENGTH_LONG).show();
            }
        }, new ItemLongClickListener() {
            @Override
            public boolean onLongClick(View v, int position) {
                Toast.makeText(context,"Long",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
