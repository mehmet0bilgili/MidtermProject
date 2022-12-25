package com.example.midtermproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class EditProfile extends Fragment {

    Button button;
    View view;
    EditText username,name,surname,education,email,gender,phoneNo,date;
    String names;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        names= getArguments().getString("prof");

        view=inflater.inflate(R.layout.fragment_edit_profile, container, false);

        name=view.findViewById(R.id.name_prof_edit);
        surname=view.findViewById(R.id.surname_prof_edit);
        email=view.findViewById(R.id.email_prof_edit);
        education=view.findViewById(R.id.education_prof_edit);
        gender=view.findViewById(R.id.gender_prof_edit);
        date=view.findViewById(R.id.birthday_prof_edit);
        phoneNo=view.findViewById(R.id.phone_prof_edit);
        getInfo();


        button = view.findViewById(R.id.save_changes_edit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Users").document(names).update("name",name.getText().toString());
                db.collection("Users").document(names).update("surname",surname.getText().toString());
                db.collection("Users").document(names).update("email",email.getText().toString());
                db.collection("Users").document(names).update("education",education.getText().toString());
                db.collection("Users").document(names).update("sex",gender.getText().toString());
                db.collection("Users").document(names).update("phoneNo",phoneNo.getText().toString());
                db.collection("Users").document(names).update("dateOfBirth",date.getText().toString());
                Toast.makeText(getContext(), "Successfully Updated!", Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }
    public void getInfo(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Users").document(names).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                name.setText(task.getResult().getString("name"));
                surname.setText(task.getResult().getString("surname"));
                email.setText(task.getResult().getString("email"));
                education.setText(task.getResult().getString("education"));
                gender.setText(task.getResult().getString("sex"));
                phoneNo.setText(task.getResult().getString("phoneNo"));
                date.setText(task.getResult().getString("dateOfBirth"));
            }
        });

    }



}