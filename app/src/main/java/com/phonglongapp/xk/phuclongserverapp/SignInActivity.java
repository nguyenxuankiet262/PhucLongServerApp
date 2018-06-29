package com.phonglongapp.xk.phuclongserverapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class SignInActivity extends AppCompatActivity {
    private ImageView logoView;
    private TextView contText;
    private Animation anim_alpha, anim_blink;
    private FirebaseAuth auth;
    //FirebaseDatabase
    FirebaseDatabase database;
    DatabaseReference user;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        logoView = findViewById(R.id.logo_image);
        contText = findViewById(R.id.continueText);
        auth = FirebaseAuth.getInstance();
        contText.setVisibility(View.INVISIBLE);
        anim_blink = new AlphaAnimation(0.0f, 1.0f);
        anim_blink.setDuration(20);
        anim_blink.setStartOffset(20);
        anim_blink.setRepeatMode(Animation.REVERSE);
        anim_blink.setRepeatCount(Animation.INFINITE);
        anim_alpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        anim_alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                contText.setVisibility(View.VISIBLE);
                contText.startAnimation(anim_blink);
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        SignInActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                login_user();
                            }
                        });
                    }
                }, 2000);
            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        logoView.startAnimation(anim_alpha);
    }

    private void login_user() {
        String mail = "admin@gmail.com";
        String pass = "123456";
        auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent mainIntent = new Intent(SignInActivity.this, HomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                }

            }
        });
    }
}
