package com.phonglongapp.xk.phuclongserverapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phonglongapp.xk.phuclongserverapp.Common.Common;
import com.phonglongapp.xk.phuclongserverapp.Model.Cart;
import com.phonglongapp.xk.phuclongserverapp.R;
import com.phonglongapp.xk.phuclongserverapp.ViewHolder.OrderDetailViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderDetailAdapter  extends RecyclerView.Adapter<OrderDetailViewHolder> {

    Context context;
    List<Cart> cartList;

    public OrderDetailAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.drink_details_item_layout,parent,false);
        return new OrderDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {
        if(cartList.get(position).getcStatus().equals("cold")) {
            Picasso.with(context).load(cartList.get(position).getcImageCold()).into(holder.imageView_history);
        }
        else if(cartList.get(position).getcStatus().equals("hot"))
        {
            Picasso.with(context).load(cartList.get(position).getcImageHot()).into(holder.imageView_history);
        }
        else {
            holder.imageView_history.setImageResource(R.drawable.thumb_default);
        }
        holder.money_drink.setText(Common.ConvertIntToMoney(cartList.get(position).getcPrice()+""));
        holder.name_drink_history.setText(cartList.get(position).getcName());
        holder.quanity_drink.setText(cartList.get(position).getcQuanity()+"");
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
