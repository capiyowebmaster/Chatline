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

import com.bumptech.glide.Glide;
import com.example.chatline.Models.statusModel;
import com.example.chatline.Models.users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;
import java.util.HashMap;

public class PostingMyImage extends AppCompatActivity {
    public TextView openGallery, postTextView,toolbarEname;
    public ImageView myImageView;
    public EditText postED;
    public final int PICK_IMAGE = 1;
    public Uri selectedImageUri;
    public FirebaseAuth mAuth;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public ProgressDialog progressDialog;
    public FirebaseUser firebaseUser;
    public FirebaseStorage firebaseStorage;
    public StorageReference storageReference;
    public String downloadedimageurl, picMessage;
    public Toolbar myToolbar;
    public CircularImageView toobarImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_my_image);

        openGallery = findViewById(R.id.imageOpenGallery);
        postTextView = findViewById(R.id.imageTextview);

        myImageView = findViewById(R.id.myImageview);
        postED = findViewById(R.id.imageEd);
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        myToolbar = findViewById(R.id.firstToolbar);
        setSupportActionBar(myToolbar);
        toobarImage=findViewById(R.id.toolbarImage);
        toolbarEname=findViewById(R.id.toolbarEname);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        postTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picMessage=postED.getText().toString();
                if (TextUtils.isEmpty(picMessage)){
                    Toast.makeText(PostingMyImage.this,"Please say somthing about your image",Toast.LENGTH_LONG).show();
                }
                else {
                    submitToStorage();

                }
            }
        });

        openGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();

            }
        });







        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Status").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                statusModel myModel = snapshot.getValue(statusModel.class);
                assert myModel != null;

                if (myModel.getStatusUrl().equals("statusUrl")){
                    toobarImage.setImageResource(R.mipmap.account_avatar_foreground);
                }
                else {
                    Glide.with(PostingMyImage.this)
                            .load(myModel.getStatusUrl())
                            .into(toobarImage);

                }
                toolbarEname.setText(myModel.getEname());


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });






























    }

    private void submitToStorage() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.setMessage("Wait till i complete my work please");
        progressDialog.show();


        StorageReference postsref= FirebaseStorage.getInstance().getReference("Posts").child(firebaseUser.
                getUid()+System.currentTimeMillis());
        postsref.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                postsref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        DatabaseReference usersRef=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                        usersRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                users myUsers=snapshot.getValue(users.class);
                                HashMap<String ,Object> hashMap= new HashMap<>();
                                hashMap.put("userid",firebaseUser.getUid());
                                hashMap.put("username",myUsers.getUsername());
                                hashMap.put("ename",myUsers.getEname());
                                hashMap.put("imageUrl",uri.toString());
                                hashMap.put("picMessage",picMessage);
                                hashMap.put("counter","0");
                                DatabaseReference postRef=FirebaseDatabase.getInstance().getReference("Posts");
                                postRef.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            progressDialog.dismiss();
                                            Toast.makeText(PostingMyImage.this,"Upload succcess",Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(PostingMyImage.this,MainActivity.class));
                                            finish();
                                        }

                                    }
                                });



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

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
            Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImageUri);
            myImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    ;
}





