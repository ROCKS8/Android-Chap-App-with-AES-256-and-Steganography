package com.example.cryptchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth auth;
    RecyclerView mainUserREcyclerView;
    UserAdapter adapter;
    FirebaseDatabase databse;
    ArrayList<Users> usersArrayList;
    ImageView img_logout;

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        databse = FirebaseDatabase.getInstance();

        usersArrayList = new ArrayList<>();

        DatabaseReference reference = databse.getReference().child("user");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    Users users = dataSnapshot.getValue(Users.class);
//                    Users users = new Users();
//                    users.setUid(dataSnapshot.child("uid").child("uid"));
//                    users.setName(dataSnapshot.child("name").toString());
//                    users.setNumber(dataSnapshot.child("number").toString());
                    usersArrayList.add(users);
//                    System.out.println("********************");
//                    System.out.println(dataSnapshot.child("uid").toString());
//                    System.out.println(dataSnapshot.child("name").toString());
//                    System.out.println(dataSnapshot.child("number").toString());
//                    System.out.println("********************");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        img_logout = findViewById(R.id.img_logout);
        mainUserREcyclerView = findViewById(R.id.mainUserRecyclerView);
        mainUserREcyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(HomeActivity.this, usersArrayList);
        mainUserREcyclerView.setAdapter(adapter);



//        img_logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Dialog dialog = new Dialog(HomeActivity.this, R.style.Dialoge);
//
//                dialog.setContentView(R.layout.dialog_logout);
//
//                TextView yes_btn, no_btn;
//
//                yes_btn = dialog.findViewById(R.id.yes_btn);
//                no_btn = dialog.findViewById(R.id.no_btn);
//
//                yes_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        FirebaseAuth.getInstance().signOut();
//                        startActivity(new Intent(HomeActivity.this, RegistrationActivity.class));
//                    }
//                });
//
//                no_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
//            }
//        });

        if(auth.getCurrentUser()==null)
        {
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
        }

    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            return;
        }else{
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}