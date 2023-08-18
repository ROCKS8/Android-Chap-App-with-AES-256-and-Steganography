package com.example.cryptchat;

//import com.example.communitychatapp.Adapter.MessagesAdapter;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.example.cryptchat.ModelClass.Messages;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;
import  android.widget.EditText;
import java.util.Date;
import android.os.Bundle;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {
    String receiverImage,receiverUid,ReceiverName,SenderUID;
    CircleImageView profile_image;
    TextView receivername;
    FirebaseDatabase database;
    FirebaseAuth firebaseauth;

    FirebaseAuth auth;

    FirebaseStorage storage;

    int c=0;
    public static String sImage;
    public static String rImage;

    public static String imgpath;

    String senderRoom,receiverRoom, rec_img;
    RecyclerView messageAdapter;
    CardView sendbtn,imgCard,iv;
    EditText messaging;
    ArrayList<Messages> messagesArrayList;
    MessagesAdapter adapter;

    Uri imageUri;
    ImagesAdapter imgadapter;
    encrypt_message encrypt = new encrypt_message();
    ImageView image,image1,sendimg,send_img,receiveimg;

    ArrayList<Images> ImgUrl;
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        database=FirebaseDatabase.getInstance();
        firebaseauth=FirebaseAuth.getInstance();
        Bundle extras = getIntent().getExtras();
        messagesArrayList=new ArrayList<>();
        ImgUrl = new ArrayList<>();
        ReceiverName=extras.getString("name");
        receiverUid=extras.getString("uid");
        receiverImage=extras.getString("receiverImage");
        profile_image=findViewById(R.id.profile_image);
        receivername=findViewById(R.id.user_name);
        messageAdapter=findViewById(R.id.messageAdapter);
        sendbtn=findViewById(R.id.sendbtn);
        iv = findViewById(R.id.camera);
        messaging=findViewById(R.id.messaging);
        storage = FirebaseStorage.getInstance();
//        send_img = findViewById(R.id.send_img);
//        receiveimg = findViewById(R.id.receive_img);
        messageAdapter=findViewById(R.id.messageAdapter);
        adapter=new MessagesAdapter(ChatActivity.this,messagesArrayList);
        imgadapter = new ImagesAdapter(ImgUrl, ChatActivity.this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ChatActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        messageAdapter.setLayoutManager(linearLayoutManager);
//        image1 = findViewById(R.id.img1);

//        imgCard = findViewById(R.id.camera);

        Picasso.get().load(receiverImage).into(profile_image);
        receivername.setText(""+ReceiverName);
        SenderUID=firebaseauth.getUid();
        senderRoom=SenderUID+receiverUid;
        receiverRoom=receiverUid+SenderUID;

        DatabaseReference reference=database.getReference().child("user").child(firebaseauth.getUid());
        DatabaseReference chatreference=database.getReference().child("chats").child(senderRoom).child("messages");
        DatabaseReference getImage = database.getReference().child("chats").child(senderRoom).child("images");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                sImage = dataSnapshot.child("imageuri").getValue().toString();
                rImage = receiverImage;
            }
            // adapter.notifyDataSetChanged();


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(StatsActivity.this, DerslerListActivity.class));
            }
        });

        chatreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messagesArrayList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Messages messages=snapshot.getValue(Messages.class);

                    if ((encrypt.decrypt(messages.msg)) == null){
                        messagesArrayList.add(messages);
                    }else{
                        messages.msg = encrypt.decrypt(messages.msg);
                        messagesArrayList.add(messages);
                    }
//                    messagesArrayList.add(messages);
                }
                adapter.notifyDataSetChanged();

            }




            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(StatsActivity.this, DerslerListActivity.class));
            }
        });







        getImage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ImgUrl.clear();
                //ImgUrl.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Messages messages=snapshot.getValue(Messages.class);
                    Images images = snapshot.getValue(Images.class);
                    // messagesArrayList.add(messages);
                    ImgUrl.add(images);
                }
                //adapter.notifyDataSetChanged();
//                imgadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(StatsActivity.this, DerslerListActivity.class));
            }
        });


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("***************************************************IV accessed******************************************************");
                //flag = 1;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                //Launch activity to get result
                someActivityResultLauncher.launch(intent);
            }

            ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                //@Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        //Intent data = result.getData();
                        imageUri = result.getData().getData();
                        //  File imgFile = new File(imageUri.toString());

                        //      image=findViewById(R.id.img);
                        //    image1=findViewById(R.id.img1);
                        // image.setImageResource(imageUri);
                        // if (imgFile.exists()) {
                        // on below line we are creating an image bitmap variable
                        // and adding a bitmap to it from image file.
                        //   Bitmap imgBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                        // on below line we are setting bitmap to our image view.
                        // image.setImageBitmap(imgBitmap);
                        //}
                        //Picasso.get().load(imageUri.toString()).into(image);
                        LinearLayoutManager Manager = new LinearLayoutManager(ChatActivity.this);
                        messageAdapter.setLayoutManager(Manager);
                        //ImagesAdapter adapter = new ImagesAdapter(ImgUrl,chat.this);
                        //messageAdapter.setAdapter(adapter);
                        Date date = new Date();
                        // Images images = new Images(imageUri.toString(), SenderUID, date.getTime());
                        // UploadImageToFirebase(imageUri);
                        System.out.println("***************************pic upload***************************");
                        c=c+1;
                        StorageReference fileRef = storage.getReference().child("upload").child(firebaseauth.getUid()).child("photo"+c);
                        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(ChatActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                                //String downloadUri = taskSnapshot.getStorage().getDownloadUrl().toString();
                                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String url = uri.toString();
                                        rec_img=url;
                                        Toast.makeText(ChatActivity.this, url, Toast.LENGTH_SHORT).show();

                                        //Do what you need to do with url
                                    }
                                /* Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                    @Override
                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                        if (!task.isSuccessful()) {
                                            throw task.getException();
                                        }

                                        // Continue with the task to get the download URL
                                        return ref.getDownloadUrl();
                                    }
                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if (task.isSuccessful()) {
                                            Uri downloadUri = task.getResult();
                                            Toast.makeText(chat.this, downloadUri.toString(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(chat.this, "no uri found", Toast.LENGTH_SHORT).show();
                                            // Handle failures
                                            // ...
                                        }
                                    }
                                });*/

                               /* if(!(downloadUri==null)) {
                                    //String generatedFilePath = downloadUri.getResult().toString();
                                    //Log.i("hiiiiii","hiii");
                                    Toast.makeText(chat.this, downloadUri,Toast.LENGTH_SHORT).show();
                                    //messaging.setText(generatedFilePath);
                                }
                            else{
                                    Toast.makeText(chat.this,"no uri found!",Toast.LENGTH_SHORT).show();
                                }*/});}});
                        // String simage = database.getReference().child("chats").child(senderRoom).child("images").push().getKey();
                        imgpath = imageUri.toString();
                        //System.out.print(imageUri);
                        // String rimage = database.getReference().child("chats").child(receiverRoom).child("images").push().getKey();
                        // Date date=new Date();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            Images images = new Images(imgpath, SenderUID,rec_img,date.getTime());
                 /*       try {
                            //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            //Bitmap receivedimage = bitmap;
                           // ByteArrayOutputStream stream = new ByteArrayOutputStream();
                           // Fragment fragment= new Fragment();
                           // ByteArrayOutputStream stream = new ByteArrayOutputStream();

                            //bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            //byte[] byteArray = stream.toByteArray();

                            //Bundle b = new Bundle();
                            //b.putParcelable("BitmapImage",bitmap);
                            //b.putByteArray("image",byteArray);


                            // your fragment code
                           // fragment.setArguments(b);
                        }*/

                            database.getReference().child("chats").child(senderRoom).child("images").push().setValue(images).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    database.getReference().child("chats").child(receiverRoom).child("images").push().setValue(images).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(Task<Void> task) {
                                            //sendimg=findViewById(R.id.img);
                                            //imgpath=imageUri.toString();
                                            // messageAdapter.setAdapter(imgadapter);
                                            // Picasso.get().load(imageUri).noPlaceholder().centerCrop().fit().into(image);
                                            //  imgpath=imageUri.toString();
                                            // messageAdapter.setAdapter(imgadapter);
                                            //receivedimage=database.getReference().child("chats").child(receiverRoom).child("images").child("img");
                                        }
                                    });
                                    messageAdapter.setAdapter(imgadapter);

                                }
                            });
                        }
                        catch(Exception e){
                            //stack.trace(e);
                            messaging.setText(e.toString());
                            // e.printStackTrace();
                            // System.out.print(e.getCause());

                        }


                    }
                }


            });

        });









        sendbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String msg=encrypt.encrypt(messaging.getText().toString());
                if(msg.isEmpty())
                {
                    return;
                }
                messaging.setText("");
                Date date=new Date();
                Messages messages=new Messages(msg,SenderUID,date.getTime());

                System.out.println("***************************encrypt************************************");
                System.out.println(msg);
                System.out.println(messages.msg);
                System.out.println(messages.sender_id);
                System.out.println(messages.Timestamp);
                System.out.println("***************************************************************");

                database.getReference().child("chats").child(senderRoom).child("messages").push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        database.getReference().child("chats").child(receiverRoom).child("messages").push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {

                            }});


                    }});


            }
        });

//        imgCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imageChooser();
//            }
//        });



        messageAdapter.setAdapter(adapter);
    }

    void imageChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
}