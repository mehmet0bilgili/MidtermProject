package com.example.midtermproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("About Us");
        setContentView(R.layout.activity_about_us);

        ImageView icon_email = findViewById(R.id.about_us_email);
        ImageView icon_github = findViewById(R.id.about_us_github);

        icon_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AboutUs.this)
                        .setTitle("Leave the app?")
                        .setMessage("You are about to leave the app and open an email app. Are you sure you want to continue?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_SENDTO);
                                intent.setData(Uri.parse("mailto:takaostark15@gmail.com"));
                                startActivity(intent);
                            }
                        }).setNegativeButton("No", null).show();
            }
        });

        icon_github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AboutUs.this)
                        .setTitle("Leave the app?")
                        .setMessage("You are about to leave the app and open GitHub. Are you sure you want to continue?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("https://github.com/mehmet0bilgili/MobilProject"));
                                startActivity(intent);
                            }
                        }).setNegativeButton("No", null).show();
            }
        });
    }

}