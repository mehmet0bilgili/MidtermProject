package com.example.midtermproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;

public class CreateAccount extends AppCompatActivity {


    EditText password,passwordAgain,username,email;
    FirebaseFirestore db;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_account);

        setTitle("CREATE ACCOUNT");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btn= findViewById(R.id.createAccountPage);
        password=findViewById(R.id.password);
        passwordAgain=findViewById(R.id.password_again);
        username=findViewById(R.id.username);
        email=findViewById(R.id.email);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=FirebaseFirestore.getInstance();
                if(checkConditions()){
                    String usernames=username.getText().toString();
                    String emails=email.getText().toString();
                    String passwords=password.getText().toString();

                    User newUser = new User(usernames,
                            passwords,
                            "",
                            "",
                            "",
                            "",
                            "",
                            emails,
                            "",
                            new ArrayList<Blog>(),
                            false);

                    db.collection("Users").document(usernames).get().
                            addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.getResult().exists()){
                                Toast.makeText(getApplicationContext(),"User Already Exist!",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                db.collection("Users").document(usernames).set(newUser).
                                        addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getApplicationContext(),"Account successfully created",Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"Task is failed",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"Task is failed",Toast.LENGTH_LONG).show();
                                    }
                                });
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"User does not exist",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }



    public boolean checkConditions() {
        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        passwordAgain=findViewById(R.id.password_again);

        if(!PasswordValidator.validate(password.getText().toString())){
            Toast.makeText(getApplicationContext(),"Please use one [0-9], [a-z], [A-Z] and at least 8 char!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!PasswordValidator.matching(password.getText().toString(),passwordAgain.getText().toString())) {
            Toast.makeText(getApplicationContext(),"Passwords does not match!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(username.getText())){
            Toast.makeText(getApplicationContext(),"Username cannot be empty!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(email.getText())){
            Toast.makeText(getApplicationContext(),"Email cannot be empty!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(password.getText())){
            Toast.makeText(getApplicationContext(),"Password cannot be empty",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}