package com.example.midtermproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateBlogFragment extends Fragment {

    View view;
    EditText title,thoughts;
    TextView author,date;
    FirebaseFirestore firestore= FirebaseFirestore.getInstance();
    Button create;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String username= getArguments().getString("activeUser");
        // Inflate the layout for this fragment
//        int leng = firestore.collection("Users").document(username).get().getResult().getString("userBlog").length();
        Date dates = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String newDate = simpleDateFormat.format(dates);

        view=inflater.inflate(R.layout.fragment_create_blog, container, false);
        title=view.findViewById(R.id.title_createBlog);
        thoughts=view.findViewById(R.id.thoughts_createBlog);

        author=view.findViewById(R.id.author_createBlog);
        date=view.findViewById(R.id.date_createBlog);
        create=view.findViewById(R.id.createBlog);
        author.setText(username);
        date.setText(newDate);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Blog blog = new Blog(0,0,0,username,newDate.toString(),thoughts.getText().toString(),
                        title.getText().toString(),username);

                if(title.getText().toString().equals("") || thoughts.getText().toString().equals("")){
                    Toast.makeText(getContext(), "No value can be empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    firestore.collection("Blog").add(blog).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(getContext(), "Blog Succesfully created", Toast.LENGTH_SHORT).show();
                            title.setText("");
                            thoughts.setText("");
                        }
                    });
                }


            }
        });


        return view;
    }
    public boolean check(){
        if(TextUtils.isEmpty(title.getText().toString()) || TextUtils.isEmpty(thoughts.getText().toString()) ){
            Toast.makeText(getContext(), "Title or Thoughts cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}