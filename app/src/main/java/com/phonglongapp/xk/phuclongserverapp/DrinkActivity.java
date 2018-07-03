package com.phonglongapp.xk.phuclongserverapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.phonglongapp.xk.phuclongserverapp.Adapter.DrinkAdapter;
import com.phonglongapp.xk.phuclongserverapp.Model.Drink;
import com.phonglongapp.xk.phuclongserverapp.Model.Rating;

import java.util.ArrayList;
import java.util.List;

public class DrinkActivity extends AppCompatActivity {

    private Toolbar mToolBar;

    String name_cate, id_cate;

    RecyclerView list_drink;

    //FirebaseDatabase
    FirebaseDatabase database;
    DatabaseReference drinks,rating;
    //Drink
    List<Drink> drinkList;

    //Adapter
    DrinkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        drinkList = new ArrayList<>();

        mToolBar = findViewById(R.id.toolbar_drink);

        database = FirebaseDatabase.getInstance();
        drinks = database.getReference("Drink");
        rating = database.getReference("Rating");

        list_drink = findViewById(R.id.listDrink);
        list_drink.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        list_drink.setHasFixedSize(true);

        //Get intent
        if(getIntent() != null){
            id_cate = getIntent().getStringExtra("CategoryId");
            name_cate = getIntent().getStringExtra("CategoryName");
        }

        setSupportActionBar(mToolBar);
        if(!id_cate.isEmpty() && id_cate != null){
            getSupportActionBar().setTitle(name_cate);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Adapter
        adapter = new DrinkAdapter(this,drinkList);
        list_drink.setAdapter(adapter);
        loadDrink(id_cate);
    }

    private void loadDrink(final String id_cate) {
        drinks.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final Drink drink = dataSnapshot.getValue(Drink.class);
                if(drink.getMenuId().equals(id_cate)) {
                    drink.setId(dataSnapshot.getKey());
                    rating.child(drink.getId()).addValueEventListener(new ValueEventListener() {
                        int count = 0, sum = 0;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Rating rate = postSnapshot.getValue(Rating.class);
                                rate.setId(dataSnapshot.getKey());
                                sum += Integer.parseInt(rate.getRate());
                                count++;
                            }
                            if (count != 0) {
                                float average = sum / count;
                                drink.setRating(average);
                                drinkList.add(drink);
                                adapter.notifyDataSetChanged();
                            }
                            else{
                                drinkList.add(drink);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Drink drink = dataSnapshot.getValue(Drink.class);
                drink.setId(dataSnapshot.getKey());
                for(int i = 0; i < drinkList.size();i++){
                    if(drinkList.get(i).getId().equals(drink.getId())){
                        drinkList.remove(i);
                        drinkList.add(i,drink);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Drink drink = dataSnapshot.getValue(Drink.class);
                drink.setId(dataSnapshot.getKey());
                for(int i = 0; i < drinkList.size();i++){
                    if(drinkList.get(i).getId().equals(drink.getId())){
                        drinkList.remove(i);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
