package com.example.chatline.Adapters;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatline.Messaging;
import com.example.chatline.Models.posts;
import com.example.chatline.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.HashMap;
import java.util.List;

public class usersAdapter extends RecyclerView.Adapter<usersAdapter.MyViewHolder> {


    private final List<posts> posts;
    private DatabaseReference mUserReference;
    public String friendsId;

    private final Context mContext;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private  int counter=0;

    public usersAdapter(Context context, List<posts> posts) {
        this.mContext = context;
        this.posts = posts;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_items, parent, false);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        return new MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        posts myPosts = posts.get(position);
        holder.usernameTv.setText(myPosts.getUsername());
        holder.enameTv.setText(myPosts.getEname());
        holder.accountimage.setImageResource(R.mipmap.account_avatar_foreground);
        Glide.with(mContext)
                .load(myPosts.getImageUrl())
                .into(holder.postImage);

        holder.picMessage.setText(myPosts.getPicMessage());
        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                counter++;
                MediaPlayer mediaPlayer = MediaPlayer.create(mContext, R.raw.like_button);
                mediaPlayer.start();
                DatabaseReference postsref=FirebaseDatabase.getInstance().getReference("Posts");
                Query query=postsref.orderByChild("counter");
               query.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                           posts checkCounter=dataSnapshot.getValue(posts.class);
                           assert checkCounter != null;
                               HashMap <String ,Object> hashMap= new HashMap<>();
                               hashMap.put("counter","capiyo");
                               dataSnapshot.getRef().child("counter").setValue(Integer.toString(counter)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void unused) {
                                       holder.counter.setText(checkCounter.getCounter());
                                   }
                               });






                           }




                       }



                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });


            }


            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });
        holder.counter.setText(myPosts.getCounter());
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        if (firebaseUser.getUid().equals(myPosts.getUserid())){
            holder.openMessaging.setVisibility(View.INVISIBLE);
        }
        else {
            holder.openMessaging.setVisibility(View.VISIBLE);
        }



        holder.openMessaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Messaging.class);
                intent.putExtra("friendsId", myPosts.getUserid());
                mContext.startActivity(intent);


            }

        });
       // holder.counter.setText(myPosts.getCounter());

    }






















    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTv, enameTv;
        public TextView openMessaging, picMessage;
        public CircularImageView accountimage;
        public  ImageView postImage;
        public LikeButton likeButton;
        public  TextView counter;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTv=itemView.findViewById(R.id.postnames);
            enameTv=itemView.findViewById(R.id.postenames);
            postImage=itemView.findViewById(R.id.postImageview);
            accountimage=itemView.findViewById(R.id.accountImageview);
            picMessage=itemView.findViewById(R.id.picMessage);
            likeButton=itemView.findViewById(R.id.likPost);
            openMessaging=itemView.findViewById(R.id.openMessaging);
            counter=itemView.findViewById(R.id.counter);




        }
    }


}