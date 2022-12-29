package com.example.midtermproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;

import java.util.ArrayList;

public class BlogDesign extends AppCompatActivity  {

    ImageButton home,profile,logout,createBlog,editprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_design);
        home=findViewById(R.id.homeButton);
        profile=findViewById(R.id.profileButton);
        logout=findViewById(R.id.logoutButton);
        editprofile= findViewById(R.id.edidProfile);
        createBlog=findViewById(R.id.createBlogButton);
        String name=getIntent().getStringExtra("username");
        setTitle("Main Page");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        replaceFragment(new HomeScreen());
        getWindow().getDecorView().setBackgroundColor(Color.rgb(226, 229, 230));
        changeBackground(profile);
        changeBackground(home);
        changeBackground(logout);
        changeBackground(createBlog);
        changeBackground(editprofile);




        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeScreen homeScreen = new HomeScreen();
                replaceFragment(homeScreen);
                Bundle bundle = new Bundle();
                bundle.putString("sended",name);
                homeScreen.setArguments(bundle);
                getWindow().getDecorView().setBackgroundColor(Color.rgb(226, 229, 230));
                changeBackground(profile);
                changeBackground(home);
                changeBackground(logout);
                changeBackground(createBlog);
                changeBackground(editprofile);
                setTitle("Main Page");
            }


        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile profiles = new Profile();
                replaceFragment(profiles);
                Bundle bundle = new Bundle();
                bundle.putString("user",name);
                profiles.setArguments(bundle);
                setTitle(name);
                getWindow().getDecorView().setBackgroundColor(Color.rgb(226, 229, 230));
                changeBackground(profile);
                changeBackground(home);
                changeBackground(logout);
                changeBackground(createBlog);
                changeBackground(editprofile);

            }


        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BlogDesign.this);
                builder.setMessage("Are you sure you want to log out?")
                        .setTitle("Log out?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        createBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateBlogFragment createBlogFragment= new CreateBlogFragment();
                Bundle bundle=new Bundle();
                bundle.putString("activeUser",name);
                createBlogFragment.setArguments(bundle);
                replaceFragment(createBlogFragment);
                getWindow().getDecorView().setBackgroundColor(Color.rgb(255, 255, 255));
                changeBackground(profile);
                changeBackground(home);
                changeBackground(logout);
                changeBackground(createBlog);
                changeBackground(editprofile);
                setTitle("Create Blog");
            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfile editprofiles = new EditProfile();
                replaceFragment(editprofiles);
                Bundle bundle = new Bundle();
                bundle.putString("prof",name);
                editprofiles.setArguments(bundle);
                setTitle("Edit Profile");

                getWindow().getDecorView().setBackgroundColor(Color.rgb(226, 229, 230));
                changeBackground(profile);
                changeBackground(home);
                changeBackground(logout);
                changeBackground(createBlog);
                changeBackground(editprofile);

            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.blogDesignFrame,fragment);
        fragmentTransaction.commit();
    }

    public void changeBackground(ImageButton button){
       button.setBackgroundColor(getWindow().getDecorView().getSolidColor());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.feedback:
                Intent intentFeedback= new Intent(BlogDesign.this,Feedback.class);
                startActivity(intentFeedback);
                return true;
            case R.id.aboutUs:
                Intent intentAboutUs = new Intent(BlogDesign.this, AboutUs.class);
                startActivity(intentAboutUs);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Log out?")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BlogDesign.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }




}