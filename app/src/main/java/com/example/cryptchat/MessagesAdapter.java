package com.example.cryptchat;


import static com.example.cryptchat.ChatActivity.sImage;
import static com.example.cryptchat.ChatActivity.rImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter{
    Context context;
    ArrayList<Messages> messagesArrayList;
    int item_send=1;
    int item_receive=2;

    public MessagesAdapter(Context context, ArrayList<Messages> messagesArrayList){
        this.context=context;
        this.messagesArrayList=messagesArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        if(viewType==item_send){
            View view=LayoutInflater.from(context).inflate(R.layout.activity_sender_layout_item,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view=LayoutInflater.from(context).inflate(R.layout.activity_receiver_layout_item,parent,false);
            return new ReceiverViewHolder(view);
        }

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder , int position){
        Messages messages=messagesArrayList.get(position);
        if(holder.getClass()==SenderViewHolder.class){
          SenderViewHolder viewHolder=(SenderViewHolder)holder;
          viewHolder.txtmessage.setText(messages.getMsg());
          Picasso.get().load(sImage).into(viewHolder.circleImageView);
        }
        else{
            ReceiverViewHolder viewHolder=(ReceiverViewHolder)holder;
            viewHolder.txtmessage.setText(messages.getMsg());
            Picasso.get().load(rImage).into(viewHolder.circleImageView);

        }
    }
    @Override
    public int getItemCount(){
        return messagesArrayList.size();
    }
    @Override
    public int getItemViewType(int position){
     Messages messages=messagesArrayList.get(position);
     if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSender_id()))
     {
         return item_send;
     }
     else
     {
         return item_receive;
     }
    }

    class SenderViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView txtmessage;
        public SenderViewHolder(@NonNull View itemView){
            super(itemView);
            circleImageView=itemView.findViewById(R.id.profile_image);
            txtmessage=itemView.findViewById(R.id.send_messages);

        }
    }
    class ReceiverViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView txtmessage;
        public ReceiverViewHolder(@NonNull View itemView){
            super(itemView);
            circleImageView=itemView.findViewById(R.id.profile_image);
            txtmessage=itemView.findViewById(R.id.get_messages);
        }
    }
}
