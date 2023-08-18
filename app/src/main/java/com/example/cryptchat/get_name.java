package com.example.cryptchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class get_name extends AppCompatActivity {

    EditText userName;
    Button next;
    FirebaseAuth auth;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_name);

        userName = findViewById(R.id.userName);
        next = findViewById(R.id.next);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userName.getText().toString() == "") {
                    userName.setError("Required");
                } else {
                    reference = database.getReference().child("user").child(auth.getUid());
                    Intent intent = getIntent();
                    String number = intent.getStringExtra("number");
                    String name = userName.getText().toString();
//                    System.out.println("\n**********************\n");
//                    System.out.println(auth.getUid());
//                    System.out.println(name);
//                    System.out.println(number);
//                    System.out.println("\n**********************\n");
                    Users users = new Users(auth.getUid(), name, number);
//                    System.out.println("\n**********************\n");
//                    System.out.println(users.getUid());
//                    System.out.println(users.getName());
//                    System.out.println(users.getNumber());
//                    System.out.println("\n**********************\n");

                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(get_name.this, HomeActivity.class));
                            }
                        }
                    });
                }
            }
        });
    }
}