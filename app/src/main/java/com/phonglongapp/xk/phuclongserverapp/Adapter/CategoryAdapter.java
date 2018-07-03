package com.phonglongapp.xk.phuclongserverapp.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.phonglongapp.xk.phuclongserverapp.Common.Common;
import com.phonglongapp.xk.phuclongserverapp.DrinkActivity;
import com.phonglongapp.xk.phuclongserverapp.HomeActivity;
import com.phonglongapp.xk.phuclongserverapp.Interface.ItemClickListener;
import com.phonglongapp.xk.phuclongserverapp.Interface.ItemLongClickListener;
import com.phonglongapp.xk.phuclongserverapp.Interface.OnActivityResult;
import com.phonglongapp.xk.phuclongserverapp.Model.Category;
import com.phonglongapp.xk.phuclongserverapp.R;
import com.phonglongapp.xk.phuclongserverapp.ViewHolder.CategoryViewHolder;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;

import static android.app.Activity.RESULT_OK;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> implements OnActivityResult {

    Context context;
    List<Category> categories;

    //Storage Firebase
    FirebaseStorage firebaseStorage;
    private StorageReference mStorageRef;

    //FirebaseDatabase
    FirebaseDatabase database;
    DatabaseReference catogoryData;

    //Progress dialog
    ProgressDialog progressDialog;

    ImageView imageView;
    MaterialEditText nameCate;
    FButton accept, cancel;
    Uri imageUri;

    Button delete, update;
    //Value Camera
    private static final int GALLERY_PICK = 1;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.category_item_layout, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, final int position) {
        Picasso picasso = Picasso.with(context);
        picasso.setIndicatorsEnabled(false);
        picasso.load(categories.get(position).getImage()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.img_product, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso picasso = Picasso.with(context);
                picasso.setIndicatorsEnabled(false);
                picasso.load(categories.get(position).getImage()).into(holder.img_product);
            }
        });
        holder.name_product.setText(categories.get(position).getName());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(context, DrinkActivity.class);
                intent.putExtra("CategoryId", categories.get(position).getId());
                intent.putExtra("CategoryName",categories.get(position).getName());
                context.startActivity(intent);
            }
        }, new ItemLongClickListener() {
            @Override
            public boolean onLongClick(View v, int position) {
                showDialogDeleteOrUpdate(position);
                return true;
            }
        });
    }

    private void showDialogDeleteOrUpdate(final int position) {
        //Init Firebase;
        mStorageRef = firebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        catogoryData = database.getReference("Category");
        final Dialog alertDialog = new Dialog(context);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.option_dialog_layout);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        update = alertDialog.findViewById(R.id.update_btn);
        delete = alertDialog.findViewById(R.id.delete_btn);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                showDialogUpdate(position);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                showDialogDelete(position);
            }
        });
        alertDialog.show();

    }

    private void showDialogDelete(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Cảnh báo!");
        builder.setIcon(R.drawable.ic_warning_black_16dp);
        builder.setMessage("Bạn có muốn xóa " + categories.get(position).getName() + " khỏi danh sách không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Chấp nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Deleting...");
                progressDialog.setMessage("Please wait for a minute!");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                catogoryData.child(categories.get(position).getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(context,"Xóa thành công!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDialogUpdate(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        View itemView = LayoutInflater.from(context).inflate(R.layout.add_dialog_layout, null);
        imageView = itemView.findViewById(R.id.choose_image);
        nameCate = itemView.findViewById(R.id.name_catelogy);
        imageUri = Uri.parse(categories.get(position).getImage());

        Picasso.with(context).load(categories.get(position).getImage()).into(imageView);
        nameCate.setText(categories.get(position).getName());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.checkChooseImageFromAdapter = true;
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                ((Activity) context).startActivityForResult(Intent.createChooser(gallery, "SELECT IMAGE"), GALLERY_PICK);
            }
        });
        accept = itemView.findViewById(R.id.accept_btn);
        cancel = itemView.findViewById(R.id.cancel_dialog);
        builder.setView(itemView);
        final AlertDialog alertDialog = builder.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null && !TextUtils.isEmpty(nameCate.getText().toString())) {
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setTitle("Uploading image...");
                    progressDialog.setMessage("Please wait for a minute!");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    if (imageUri.equals(Uri.parse(categories.get(position).getImage()))
                            && !nameCate.getText().toString().equals(categories.get(position).getName())) {
                        catogoryData.child(categories.get(position).getId()).child("name").setValue(nameCate.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            alertDialog.dismiss();
                                            Toast.makeText(context, "Update thành công!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    } else if (!imageUri.equals(Uri.parse(categories.get(position).getImage()))
                            && nameCate.getText().toString().equals(categories.get(position).getName())) {
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setTitle("Uploading image...");
                        progressDialog.setMessage("Please wait for a minute!");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                        final StorageReference riversRef = mStorageRef.child(String.valueOf(System.currentTimeMillis()) + ".jpg");
                        riversRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String downloadUrl = uri.toString();
                                        catogoryData.child(categories.get(position).getId()).child("image").setValue(downloadUrl)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            progressDialog.dismiss();
                                                            alertDialog.dismiss();
                                                            Toast.makeText(context, "Update thành công!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                });
                            }
                        });
                    }
                    else if(!imageUri.equals(Uri.parse(categories.get(position).getImage()))
                            && !nameCate.getText().toString().equals(categories.get(position).getName())){
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setTitle("Uploading image...");
                        progressDialog.setMessage("Please wait for a minute!");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                        final StorageReference riversRef = mStorageRef.child(String.valueOf(System.currentTimeMillis()) + ".jpg");
                        riversRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String downloadUrl = uri.toString();
                                        Category category = new Category();
                                        category.setImage(downloadUrl);
                                        category.setName(nameCate.getText().toString());
                                        catogoryData.child(categories.get(position).getId()).setValue(category)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            progressDialog.dismiss();
                                                            alertDialog.dismiss();
                                                            Toast.makeText(context, "Update thành công!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                });
                            }
                        });
                    }
                    else {

                    }
                } else {
                    Toast.makeText(context, "Nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                imageUri = data.getData();
                InputStream imageStream = null;
                imageStream = context.getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);

                imageUri = data.getData();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(context, "Lỗi chọn ảnh", Toast.LENGTH_LONG).show();
            }
        }
    }
}
