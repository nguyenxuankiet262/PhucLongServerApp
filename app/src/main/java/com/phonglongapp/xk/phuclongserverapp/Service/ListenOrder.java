package com.phonglongapp.xk.phuclongserverapp.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.phonglongapp.xk.phuclongserverapp.Model.Order;
import com.phonglongapp.xk.phuclongserverapp.OrderActivity;
import com.phonglongapp.xk.phuclongserverapp.R;

import java.util.Random;

public class ListenOrder extends Service implements ChildEventListener {

    FirebaseDatabase database;
    DatabaseReference order;

    @Override
    public void onCreate() {
        super.onCreate();
        database = FirebaseDatabase.getInstance();
        order = database.getReference("Order");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        order.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    public ListenOrder() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Order temp = dataSnapshot.getValue(Order.class);
        temp.setId(dataSnapshot.getKey());
        if (temp.getStatus().equals("0")) {
            showNofication(dataSnapshot.getKey(), temp, "0");
        }
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Order temp = dataSnapshot.getValue(Order.class);
        temp.setId(dataSnapshot.getKey());
        if (temp.getStatus().equals("0")) {
            showNofication(dataSnapshot.getKey(), temp, "0");
        }
        if (temp.getStatus().equals("1")) {
            showNofication(dataSnapshot.getKey(), temp, "1");
        }
        if (temp.getStatus().equals("2")) {
            showNofication(dataSnapshot.getKey(), temp, "2");
        }
        if (temp.getStatus().equals("3")) {
            showNofication(dataSnapshot.getKey(), temp, "3");
        }

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }


    private void showNofication(String key, Order temp, String s) {
        Intent intent = new Intent(getBaseContext(), OrderActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setTicker("Phúc Long Coffee & Tea Exprees")
                .setSmallIcon(R.drawable.ic_local_drink_black_24dp)
                .setContentIntent(pendingIntent);
                if(s.equals("0")){
                    builder.setContentInfo("New Order")
                            .setContentText("Có order mới #" + key);
                }
                if(s.equals("1")){
                    builder.setContentInfo("Shipping")
                            .setContentText("Order #" + key+ " on the way");
                }
                if(s.equals("2")){
                    builder.setContentInfo("Success")
                            .setContentText("Order #" + key + "  is paid");
                }
                if(s.equals("3")){
                    builder.setContentInfo("Cancel")
                            .setContentText("Order #"+ key + " is cancled");
                }

        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        int id = new Random().nextInt(9999 - 1) + 1;
        manager.notify(id, builder.build());
    }
}
