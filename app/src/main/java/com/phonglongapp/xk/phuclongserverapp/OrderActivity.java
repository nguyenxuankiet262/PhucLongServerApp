package com.phonglongapp.xk.phuclongserverapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.phonglongapp.xk.phuclongserverapp.Adapter.OrderAdapter;
import com.phonglongapp.xk.phuclongserverapp.Model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RecyclerView list_Order;
    //Adapter
    OrderAdapter adapter;
    //Firebase
    FirebaseDatabase database;
    DatabaseReference order;
    //List
    List<Order> orderList;

    RelativeLayout emptyOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderList = new ArrayList<>();
        list_Order = findViewById(R.id.order_list);
        bottomNavigationView = findViewById(R.id.bot_bar);
        emptyOrder = findViewById(R.id.empty_order);
        emptyOrder.setVisibility(View.INVISIBLE);

        database = FirebaseDatabase.getInstance();
        order = database.getReference("Order");

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        list_Order.setLayoutManager(mLayoutManager);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.order_new){
                    loadOrder("0");
                }
                else if(item.getItemId() == R.id.order_ontheway){
                    loadOrder("1");
                }
                else if(item.getItemId() == R.id.order_paid){
                    loadOrder("2");
                }
                else if(item.getItemId() == R.id.order_remove){
                    loadOrder("3");
                }
                return true;
            }
        });
        adapter = new OrderAdapter(this,orderList);
        loadOrder("0");
    }

    private void loadOrder(String s) {
        if(s.equals("0")) {
            order.orderByChild("status").equalTo("0").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    orderList.clear();
                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                        Order temp = post.getValue(Order.class);
                        temp.setId(post.getKey());
                        //Toast.makeText(OrderActivity.this, temp.getId() +"\n" + temp.getName() + "\n" + temp.getStatus(), Toast.LENGTH_SHORT).show();
                        orderList.add(temp);
                    }
                    adapter.notifyDataSetChanged();
                    if(orderList.size() == 0){
                        emptyOrder.setVisibility(View.VISIBLE);
                    }
                    else {
                        emptyOrder.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            list_Order.setAdapter(adapter);
        }
        else if (s.equals("1")){
            order.orderByChild("status").equalTo("1").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    orderList.clear();
                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                        Order temp = post.getValue(Order.class);
                        temp.setId(post.getKey());
                        //Toast.makeText(OrderActivity.this, temp.getId() +"\n" + temp.getName() + "\n" + temp.getStatus(), Toast.LENGTH_SHORT).show();
                        orderList.add(temp);
                    }
                    adapter.notifyDataSetChanged();
                    if(orderList.size() == 0){
                        emptyOrder.setVisibility(View.VISIBLE);
                    }
                    else {
                        emptyOrder.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            list_Order.setAdapter(adapter);
        }
        else if(s.equals("2")){
            order.orderByChild("status").equalTo("2").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    orderList.clear();
                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                        Order temp = post.getValue(Order.class);
                        temp.setId(post.getKey());
                        //Toast.makeText(OrderActivity.this, temp.getId() +"\n" + temp.getName() + "\n" + temp.getStatus(), Toast.LENGTH_SHORT).show();
                        orderList.add(temp);
                    }
                    adapter.notifyDataSetChanged();
                    if(orderList.size() == 0){
                        emptyOrder.setVisibility(View.VISIBLE);
                    }
                    else {
                        emptyOrder.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            list_Order.setAdapter(adapter);
        }
        else if (s.equals("3")){
            order.orderByChild("status").equalTo("3").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    orderList.clear();
                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                        Order temp = post.getValue(Order.class);
                        temp.setId(post.getKey());
                        //Toast.makeText(OrderActivity.this, temp.getId() +"\n" + temp.getName() + "\n" + temp.getStatus(), Toast.LENGTH_SHORT).show();
                        orderList.add(temp);
                    }
                    adapter.notifyDataSetChanged();
                    if(orderList.size() == 0){
                        emptyOrder.setVisibility(View.VISIBLE);
                    }
                    else {
                        emptyOrder.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            list_Order.setAdapter(adapter);
        }
    }
}
