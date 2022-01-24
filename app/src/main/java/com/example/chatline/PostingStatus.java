package com.example.chatline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatline.Models.users;
import com.example.chatline.Models.users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;
import java.util.HashMap;

public class PostingStatus extends AppCompatActivity {

    public TextView openGallery, postTextView;
    public ImageView myImageView;
    public final int PICK_IMAGE = 1;
    public Uri selectedImageUri;
    public FirebaseAuth mAuth;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public ProgressDialog progressDialog;
    public FirebaseUser firebaseUser;
    public FirebaseStorage firebaseStorage;
    public StorageReference storageReference;
    public String downloadedimageurl, postingMessage;
    public Toolbar myToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_status);
        openGallery = findViewById(R.id.statusOpenGallery);
        postTextView = findViewById(R.id.statusTextview);
        myImageView = findViewById(R.id.statusImageView);
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myToolbar = findViewById(R.id.firstToolbar);
        firebaseUser=mAuth.getCurrentUser();
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        postTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitToStorage();

            }
        });


        openGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();

            }
        });
    }

    public void submitToStorage() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.setMessage("Wait till i complete my work please");
        progressDialog.show();
        // submit to storage

                        StorageReference storageReference=FirebaseStorage.getInstance().getReference("Status").child(firebaseUser.getUid());
                        storageReference.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri downloadeduri) {
                                        HashMap <String,Object> hashMap= new HashMap<>();
                                        hashMap.put("statusUrl", downloadeduri.toString());
                                        DatabaseReference statusRef=FirebaseDatabase.getInstance().getReference("Status").child(firebaseUser.getUid());
                                        statusRef.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressDialog.dismiss();
                                                Toast.makeText(getApplicationContext()," Upload success",Toast.LENGTH_LONG).show();
                                                finish();

                                            }
                                        });


                                    }
                                });


                            }
                        });

                    }












    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "selectImage"), PICK_IMAGE);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        try {
            selectedImageUri=data.getData();
            Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImageUri);
            myImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    ;
}





