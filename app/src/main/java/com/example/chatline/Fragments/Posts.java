package com.example.chatline.Fragments;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.chatline.Adapters.PostsAdapter;
import com.example.chatline.Models.posts;
import com.example.chatline.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class Posts extends Fragment {
    public TextView postVideos;
    public VideoView postImageView;
    public final int PICK_VIDEO = 1;
    public Uri selectedVideoUri;
    public FirebaseAuth mAuth;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public ProgressDialog progressDialog;
    public FirebaseUser firebaseUser;
    public FirebaseStorage firebaseStorage;
    public StorageReference storageReference;
    public String videoUrl, postingMessage;
    public RecyclerView recyclerView;
    public PostsAdapter postsAdapter;
    public List<posts> myPosts;



    public Posts() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragments_posts, container, false);


        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();

        recyclerView = view.findViewById(R.id.newsRecylcerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myPosts= new ArrayList<>();


        readPosts();



        return view;
    }

    private void readPosts() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.setMessage("Wait till i complete my work please");
        progressDialog.show();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myPosts.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    posts model=dataSnapshot.getValue(posts.class);
                    assert model != null;
                    myPosts.add(model);
                    progressDialog.dismiss();

                }
                postsAdapter=new PostsAdapter(getContext(),myPosts);
                recyclerView.setAdapter(postsAdapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });

    }

    }





