package com.example.midtermproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Feedback extends AppCompatActivity {

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle("FEEDBACK");

        RatingBar feedbackRating = findViewById(R.id.feedback_rating);
        EditText feedbackMessage = findViewById(R.id.feedback_message);
        Button feedbackButton = findViewById(R.id.feedback_button);

        firestore = FirebaseFirestore.getInstance();

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float ratingData = feedbackRating.getRating();
                String messageData = feedbackMessage.getText().toString();

                if (TextUtils.isEmpty(messageData) || ratingData<1) {
                    Toast.makeText(Feedback.this,
                            "Do not leave blanks empty!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //sending the data to firestore
                    firestore.collection("Feedbacks").add(new FeedbackObject(ratingData, messageData)).
                            addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(Feedback.this,
                                            "Thank you for the feedback!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Feedback.this,
                                            "Failed to send the feedback!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                    //changing the page
                    finish();
                    //Intent toHomeIntent = new Intent(Feedback.this, HomeScreen.class);
                    //startActivity(toHomeIntent);
                }
            }
        });

    }

}