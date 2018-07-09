package com.phonglongapp.xk.phuclongserverapp.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.phonglongapp.xk.phuclongserverapp.Common.Common;
import com.phonglongapp.xk.phuclongserverapp.Interface.ItemClickListener;
import com.phonglongapp.xk.phuclongserverapp.Interface.ItemLongClickListener;
import com.phonglongapp.xk.phuclongserverapp.Model.Cart;
import com.phonglongapp.xk.phuclongserverapp.Model.MyResponse;
import com.phonglongapp.xk.phuclongserverapp.Model.Notification;
import com.phonglongapp.xk.phuclongserverapp.Model.Order;
import com.phonglongapp.xk.phuclongserverapp.Model.Sender;
import com.phonglongapp.xk.phuclongserverapp.Model.Token;
import com.phonglongapp.xk.phuclongserverapp.R;
import com.phonglongapp.xk.phuclongserverapp.Retrofit.APIService;
import com.phonglongapp.xk.phuclongserverapp.ViewHolder.OrderViewHolder;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    Context context;
    List<Order> orderList;
    FrameLayout details, info;
    RecyclerView list_order;
    TextView id_order, time_order, status_order, comment_order;
    FButton nextBtn;
    List<Cart> cartList;
    OrderDetailAdapter adapter;

    //Firebase
    FirebaseDatabase database;
    DatabaseReference order;
    APIService apiService;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.order_item_layout, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        apiService = Common.getFCMService();
        holder.time_order.setText(Common.getTimeAgo(Long.parseLong(orderList.get(position).getId()), context));
        holder.id_order.setText("#" + orderList.get(position).getId());
        if (orderList.get(position).getStatus().equals("0")) {
            holder.status_order.setText("New Order");
            holder.status_order.setTextColor(ContextCompat.getColor(context, R.color.colorOpenStore));
        }
        if (orderList.get(position).getStatus().equals("1")) {
            holder.status_order.setText("On the way");
            holder.status_order.setTextColor(ContextCompat.getColor(context, R.color.colorOTW));
        }
        if (orderList.get(position).getStatus().equals("2")) {
            holder.status_order.setText("Success");
            holder.status_order.setTextColor(ContextCompat.getColor(context, R.color.colorSc));
        }
        if (orderList.get(position).getStatus().equals("3")) {
            holder.status_order.setText("Delete");
            holder.status_order.setTextColor(ContextCompat.getColor(context, R.color.colorCancel));
        }
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, final int position) {
                cartList = new ArrayList<>();
                cartList = orderList.get(position).getCartList();
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final View itemView = LayoutInflater.from(context).inflate(R.layout.order_layout, null);
                details = itemView.findViewById(R.id.order_details);
                id_order = details.findViewById(R.id.id_order_history);
                time_order = details.findViewById(R.id.time_order_history);
                status_order = details.findViewById(R.id.status_order_history);
                comment_order = details.findViewById(R.id.comment_order_history);
                nextBtn = details.findViewById(R.id.ship_btn);

                list_order = details.findViewById(R.id.list_order_history);
                list_order.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                list_order.setHasFixedSize(true);

                adapter = new OrderDetailAdapter(context, cartList);
                list_order.setAdapter(adapter);

                id_order.setText("#" + orderList.get(position).getId());
                time_order.setText(Common.getTimeAgo(Long.parseLong(orderList.get(position).getId()), context));
                if (orderList.get(position).getStatus().equals("0")) {
                    status_order.setText("New Order");
                    status_order.setTextColor(ContextCompat.getColor(context, R.color.colorOpenStore));
                }
                if (orderList.get(position).getStatus().equals("1")) {
                    status_order.setText("On the way");
                    status_order.setTextColor(ContextCompat.getColor(context, R.color.colorOTW));
                }
                if (orderList.get(position).getStatus().equals("2")) {
                    status_order.setText("Success");
                    status_order.setTextColor(ContextCompat.getColor(context, R.color.colorSc));
                }
                if (orderList.get(position).getStatus().equals("3")) {
                    status_order.setText("Delete");
                    status_order.setTextColor(ContextCompat.getColor(context, R.color.colorCancel));
                }
                builder.setView(itemView);
                final AlertDialog alertDialog = builder.show();
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        database = FirebaseDatabase.getInstance();
                        order = database.getReference("Order");
                        order.child(orderList.get(position).getId()).child("status").setValue("1");
                        sendNoti(orderList.get(position).getUserID(), orderList.get(position).getId(), alertDialog);
                    }
                });
            }
        }, new ItemLongClickListener() {
            @Override
            public boolean onLongClick(View v, int position) {

                Toast.makeText(context, "Long", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void sendNoti(final String userID, final String id, AlertDialog alertDialog) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Token");
        tokens.orderByKey().equalTo(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot post : dataSnapshot.getChildren()) {
                    Token serverToken = post.getValue(Token.class);
                    Notification notification = new Notification("Order #" + id + " đang được ship", "Phúc Long Coffee and Tea Express");
                    Sender content = new Sender(serverToken.getToken(), notification);

                    apiService.sendNoti(content).enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                            if (response.body().success == 1) {
                                Toast.makeText(context, "Order #" + id + " đang được ship", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(context, "Lỗi bất ngờ", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MyResponse> call, Throwable t) {
                            Log.e("Errorr", t.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
