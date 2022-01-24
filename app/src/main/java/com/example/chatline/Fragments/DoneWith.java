package com.example.chatline.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.chatline.Adapters.itemsAdapter;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.chatline.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class DoneWith extends Fragment {
    private static final int PICK_IMAGE = 1;
    public TextView postItems;
    public TextView postVideos;
    public VideoView postImageView;
    public Uri selectedImageUri;
    public FirebaseAuth mAuth;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public ProgressDialog progressDialog;
    public FirebaseUser firebaseUser;
    public FirebaseStorage firebaseStorage;
    public StorageReference storageReference;
    public String videoUrl, postingMessage;
    public RecyclerView recyclerView;



    public DoneWith() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_done_with, container, false);
       firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        return  view;


    }



}