package com.phonglongapp.xk.phuclongserverapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phonglongapp.xk.phuclongserverapp.Common.Common;
import com.phonglongapp.xk.phuclongserverapp.Model.Drink;
import com.phonglongapp.xk.phuclongserverapp.R;
import com.phonglongapp.xk.phuclongserverapp.ViewHolder.DrinkViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkViewHolder> {

    Context context;
    List<Drink> drinkList;

    public DrinkAdapter(Context context, List<Drink> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.drink_item_layout, parent, false);
        return new DrinkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DrinkViewHolder holder, final int position) {
        holder.name_drink.setText(drinkList.get(position).getName());
        holder.price_drink.setText(Common.ConvertIntToMoney(drinkList.get(position).getPrice()));
        if (!drinkList.get(position).getImage_cold().equals("empty")) {
            Picasso.with(context).load(drinkList.get(position).getImage_cold()).into(holder.image_drink);
        }
        else if (!drinkList.get(position).getImage_hot().equals("empty")) {
            Picasso.with(context).load(drinkList.get(position).getImage_hot()).into(holder.image_drink);
        }
        else {
            holder.image_drink.setImageResource(R.drawable.thumb_default);
        }

        if (drinkList.get(position).getImage_cold().equals("empty")) {
            holder.cold_btn.setVisibility(View.INVISIBLE);
        }
        if (drinkList.get(position).getImage_hot().equals("empty")) {
            holder.hot_btn.setVisibility(View.INVISIBLE);
        }
        if (drinkList.get(position).getImage_hot().equals("empty") && drinkList.get(position).getImage_cold().equals("empty")) {
            holder.hot_btn.setVisibility(View.INVISIBLE);
            holder.cold_btn.setVisibility(View.INVISIBLE);
        }
        holder.cold_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(context).load(drinkList.get(position).getImage_cold()).into(holder.image_drink);
            }
        });
        holder.hot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(context).load(drinkList.get(position).getImage_hot()).into(holder.image_drink);
            }
        });
        holder.ratingBar.setRating(drinkList.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
}
