package com.example.midtermproject;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    EditText usernames,passwords;
    CheckBox remember ;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.logo);

        setTitle("WELCOME");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream in;
                Bitmap bmp;

                int responseCode;
                try{

                    URL url = new URL("https://www.sitereportcard.com/wp-content/uploads/2018/07/blog-post-ideas.jpeg");//"http://192.xx.xx.xx/mypath/img1.jpg
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    con.setDoInput(true);
                    con.connect();
                    responseCode = con.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK)
                    {
                        //download
                        in = con.getInputStream();
                        bmp = BitmapFactory.decodeStream(in);
                        final  Bitmap b = bmp;
                        in.close();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(b);
                            }
                        });
                    }

                }
                catch(Exception ex){
                    Log.e("Exception",ex.toString());
                }

            }
        }).start();

        TextView sign= findViewById(R.id.createAccount);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        usernames=findViewById(R.id.username_main);
        passwords=findViewById(R.id.password_main);
        remember = findViewById(R.id.rememberMes);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(MainActivity.this,CreateAccount.class);
                startActivity(intent1);
            }
        });

        Button btn = findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Users").document(usernames.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()) {
                            if (TextUtils.equals(task.getResult().getString("username"),usernames.getText().toString()) && TextUtils.equals(task.getResult().getString("password"),passwords.getText().toString())) {
                                SharedPreferences pref = getSharedPreferences("apppref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                if (remember.isChecked()) {
                                    editor.putString("username", usernames.getText().toString());
                                    editor.putString("password", passwords.getText().toString());

                                } else {
                                    editor.remove("username");
                                    editor.remove("password");
                                }
                                editor.putBoolean("rememberMe", remember.isChecked());
                                editor.commit();
                                Intent intent = new Intent(MainActivity.this, BlogDesign.class);
                                intent.putExtra("username", usernames.getText().toString());
                                Toast.makeText(getApplicationContext(), "Successfully Logged In Welcome", Toast.LENGTH_LONG).show();
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Username or password wrong!",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "User does not exist!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        });
        SharedPreferences pref = getSharedPreferences("apppref", Context.MODE_PRIVATE);//?
        String usernamess = pref.getString("username", "");
        passwords.setText(pref.getString("password", ""));
        usernames.setText(usernamess);
        remember.setChecked(pref.getBoolean("rememberMe", false));
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit the app?")
                .setMessage("Are you sure you want to exit the app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}