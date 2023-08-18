package com.example.cryptchat;
import androidx.recyclerview.widget.RecyclerView;
import static com.example.cryptchat.ChatActivity.sImage;
import static com.example.cryptchat.ChatActivity.rImage;
import static com.example.cryptchat.ChatActivity.imgpath;
import static com.example.cryptchat.ChatActivity.rImage;

import android.content.ContentResolver;
import androidx.fragment.app.Fragment;
//import static com.example.communitychatapp.chat.receivedimage;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.module.AppGlideModule;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ViewGroup;
import java.util.ArrayList;
import android.content.Context;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import 	android.graphics.BitmapFactory;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.github.bumptech.glide;

import com.example.cryptchat.MessagesAdapter;
import com.example.cryptchat.Users;
import com.example.cryptchat.R;
import com.example.cryptchat.ChatActivity;
import com.squareup.picasso.Picasso;
import android.app.Activity;
import com.google.firebase.auth.FirebaseAuth;
//import static com.example.communitychatapp.chat.imageUri;

import android.content.Intent;
import com.example.cryptchat.Messages;
import com.example.cryptchat.Images;

import com.example.cryptchat.R;


public class ImagesAdapter extends RecyclerView.Adapter {

    ArrayList<Images> urls;
    Context context;
    int item_send = 1;
    int item_receive = 2;
   // byte[] byteArray = ((Activity) context).getIntent().getByteArrayExtra("image");

    //constructor
    public ImagesAdapter(ArrayList<Images> urls, Context context) {
        this.urls = urls;
        this.context = context;
    }

    /*  public static class ViewHolder extends RecyclerView.ViewHolder
      {
          private ImageView image;

          public ViewHolder(View v)
          {
              super(v);
              image =(ImageView)v.findViewById(R.id.img);
          }

          public ImageView getImage(){ return this.image;}
      }*/
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == item_send) {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_listitem, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_listitem1, parent, false);
            return new ReceiverViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Images images = urls.get(position);
        if (holder.getClass() == SenderViewHolder.class) {
            SenderViewHolder viewHolder = (SenderViewHolder) holder;
            //Glide.with(this.context).load(urls.get(position)).into(viewHolder.imageview);
            Picasso.get().load(images.getImg()).into(viewHolder.imageview);
        }

        //if(holder.getClass()== MessagesAdapter.SenderViewHolder.class){
        //MessagesAdapter.SenderViewHolder viewHolder=(MessagesAdapter.SenderViewHolder)holder;
        //viewHolder.txtmessage.setText(messages.getMsg());
          //Picasso.get().load(sImage).into(viewHolder.circleImageView);

        else if (holder.getClass() == ReceiverViewHolder.class) {
            ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
            Picasso.get().load(rImage).into(viewHolder.circleImageView);
           // byte[] byteArray = getIntent().getByteArrayExtra("image");
           // byte[] byteArray = getArguments().getByteArrayExtra("image");
            //Bundle b = new Bundle();
            //itmap bitmap = b.getParcelable("BitmapImage");

            //Bitmap bitmap = Images.Media.getBitmap(this.getContentResolver(),images.getImg());
            //Bitmap receivedimage = bitmap;
           // Glide.with(this.context).load(urls.get(position)).into(viewHolder.imageview);
            Picasso.get().load(images.getReceived_img()).into(viewHolder.imageview);
           // viewHolder.imageview.setImageBitmap(images.getReceived_img());

        }

    }


    @Override
    public int getItemCount() {
        return urls.size();
    }


    @Override
    public int getItemViewType(int position) {
        Images images = urls.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(images.getSender_id())) {
            return item_send;
        } else {
            return item_receive;
        }
    }
}
class SenderViewHolder extends RecyclerView.ViewHolder{
    CircleImageView circleImageView;
    ImageView imageview;
    TextView txtmessage;
    public SenderViewHolder(@NonNull View itemView){
        super(itemView);
        imageview=itemView.findViewById(R.id.img);
        circleImageView=itemView.findViewById(R.id.profile_image);
        //imageview.setImageUri(imgpath);
        //txtmessage=itemView.findViewById(R.id.send_messages);

    }
}
class ReceiverViewHolder extends RecyclerView.ViewHolder{
    CircleImageView circleImageView;
    ImageView imageview;
    TextView txtmessage;
    public ReceiverViewHolder(@NonNull View itemView){
        super(itemView);
        imageview=itemView.findViewById(R.id.img1);
        circleImageView=itemView.findViewById(R.id.profile_image);
       // txtmessage=itemView.findViewById(R.id.get_messages);
    }
}
