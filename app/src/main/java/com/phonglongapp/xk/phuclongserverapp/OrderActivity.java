package com.phonglongapp.xk.phuclongserverapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
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
    DatabaseReference order,backup;
    //List
    List<Order> orderList;

    RelativeLayout emptyOrder;

    Toolbar toolbar;

    String check = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        toolbar = findViewById(R.id.toolbar_order);
        setSupportActionBar(toolbar);

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
                    toolbar.setTitle("NEW ORDER");
                    check = "0";
                }
                else if(item.getItemId() == R.id.order_ontheway){
                    loadOrder("1");
                    toolbar.setTitle("SHIPPING");
                    check = "1";
                }
                else if(item.getItemId() == R.id.order_paid){
                    loadOrder("2");
                    toolbar.setTitle("SUCCESS");
                    check = "2";
                }
                else if(item.getItemId() == R.id.order_remove){
                    loadOrder("3");
                    toolbar.setTitle("DELETED ORDER");
                    check = "3";
                }
                return true;
            }
        });
        adapter = new OrderAdapter(this,orderList);
        loadOrder("0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_all_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_delete:
                View menuItem = findViewById(R.id.ic_delete);
                showPopup(menuItem);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPopup(View menuItem) {
        final AlertDialog alertDialog = new AlertDialog.Builder(OrderActivity.this).create();
        String s = "New Order";
        if(check.equals("0")){
            s = "New Order";
        }
        else if(check.equals("1")){
            s = "Shipping Order";
        }
        else if(check.equals("2")){
            s = "Success Order";
        }
        else if (check.equals("3")){
            s = "Deleted Order";
        }

        alertDialog.setMessage("Bạn có muốn xóa tất cả " + s + " không?");
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Chấp nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(OrderActivity.this);
                progressDialog.setTitle("Deleting...");
                progressDialog.setMessage("Please wait for a minute!");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                backup = database.getReference("Backup");
                order.orderByChild("status").equalTo(check).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String t = "New";
                        if(check.equals("0")){
                            t = "New";
                        }
                        else if(check.equals("1")){
                            t = "Shipping";
                        }
                        else if(check.equals("2")){
                            t = "Success";
                        }
                        else if (check.equals("3")){
                            t = "Deleted";
                        }
                        for(DataSnapshot post : dataSnapshot.getChildren()){
                            backup.child(t).child(post.getKey()).setValue(orderList);
                            order.child(post.getKey()).removeValue();
                        }
                        alertDialog.dismiss();
                        progressDialog.dismiss();
                        Toast.makeText(OrderActivity.this,"Xóa thành công!",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        alertDialog.show();
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
