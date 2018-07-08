package com.phonglongapp.xk.phuclongserverapp.ViewHolder;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phonglongapp.xk.phuclongserverapp.R;

public class OrderDetailViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView_history;
    public TextView name_drink_history, money_drink, quanity_drink;

    public OrderDetailViewHolder(View itemView) {
        super(itemView);
        imageView_history = itemView.findViewById(R.id.image_drink_history);
        name_drink_history = itemView.findViewById(R.id.name_drink_history);
        quanity_drink = itemView.findViewById(R.id.quanity_drink_history);
        money_drink = itemView.findViewById(R.id.money_drink_history);
    }
}
