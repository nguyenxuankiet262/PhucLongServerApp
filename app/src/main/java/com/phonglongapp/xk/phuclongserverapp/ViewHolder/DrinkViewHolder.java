package com.phonglongapp.xk.phuclongserverapp.ViewHolder;

import android.media.Rating;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.phonglongapp.xk.phuclongserverapp.Interface.ItemClickListener;
import com.phonglongapp.xk.phuclongserverapp.Interface.ItemLongClickListener;
import com.phonglongapp.xk.phuclongserverapp.R;

public class DrinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public ImageView image_drink, cold_btn, hot_btn;
    public TextView name_drink, price_drink;
    public RatingBar ratingBar;

    ItemClickListener itemClickListener;
    ItemLongClickListener itemLongClickListener;

    public DrinkViewHolder(View itemView) {
        super(itemView);
        image_drink = itemView.findViewById(R.id.image_drink);
        cold_btn = itemView.findViewById(R.id.cold_drink_image);
        hot_btn = itemView.findViewById(R.id.hot_drink_image);
        name_drink = itemView.findViewById(R.id.name_drink);
        price_drink = itemView.findViewById(R.id.price_drink);
        ratingBar = itemView.findViewById(R.id.rating_bar);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener, ItemLongClickListener itemLongClickListener)
    {
        this.itemClickListener = itemClickListener;
        this.itemLongClickListener = itemLongClickListener;
    }
    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View v) {
        return itemLongClickListener.onLongClick(v,getAdapterPosition());
    }
}
