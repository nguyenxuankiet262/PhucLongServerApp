package com.phonglongapp.xk.phuclongserverapp;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.phonglongapp.xk.phuclongserverapp.Model.Rating;

import java.util.List;

public class CommentActivity extends AppCompatActivity {

    //Firebase
    FirebaseDatabase database;
    DatabaseReference rating;

    BottomNavigationView bottomNavigationView;
    RecyclerView list_Cmt;

    List<Rating> ratingList;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        toolbar = findViewById(R.id.toobar_feedback);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_all_menu, menu);
        return true;
    }
}
