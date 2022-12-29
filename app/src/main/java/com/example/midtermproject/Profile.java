package com.example.midtermproject;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;


public class Profile extends Fragment {

    Uri imageUri;
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference("uploads/");
    StorageTask storageTask;
    ImageView profileImg;
    Button myBlogsBtn;
    View view;
    String names;

    //1
    private static final int PICK_IMAGE_REQUEST = 1;
    //1

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        view=inflater.inflate(R.layout.fragment_profile, container, false);
        names = getArguments().getString("user");
        profileImg= view.findViewById(R.id.img_prof);
        myBlogsBtn = view.findViewById(R.id.showBlog);

        new Thread(new Runnable() {
            @Override
            public void run() {
                db.collection("Users").document(names).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().getBoolean("rememberMe")== true){
                                try {
                                    File localFile = File.createTempFile("temp",".jpg");
                                    StorageReference st = FirebaseStorage.getInstance().getReference("uploads/"+names+".jpg");
                                    st.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                            profileImg.setImageBitmap(bitmap);
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
            }
        }).start();


        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(storageTask != null && storageTask.isInProgress()){
                    Toast.makeText(getContext(), "upload in another process", Toast.LENGTH_SHORT).show();
                }
                else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, PICK_IMAGE_REQUEST);
                        }
                    }).start();
                }
            }
        });

        myBlogsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBlogs myBlogs = new MyBlogs();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.blogDesignFrame,myBlogs);
                fragmentTransaction.commit();
                Bundle bundle = new Bundle();
                bundle.putString("bloguser",names);
                myBlogs.setArguments(bundle);
            }
        });
        //2

        TextView name,surname,education,email,gender,phoneNo,birthday;

        name=view.findViewById(R.id.name_prof);
        surname=view.findViewById(R.id.surname_prof);
        email=view.findViewById(R.id.email_prof);
        education=view.findViewById(R.id.education_prof);
        gender=view.findViewById(R.id.gender_prof);
        phoneNo=view.findViewById(R.id.phone_prof);
        birthday=view.findViewById(R.id.birthday_prof);

        db.collection("Users").document(names).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){

                    name.setText(task.getResult().getString("name"));
                    surname.setText(task.getResult().getString("surname"));
                    email.setText(task.getResult().getString("email"));
                    education.setText(task.getResult().getString("education"));
                    gender.setText(task.getResult().getString("sex"));
                    phoneNo.setText(task.getResult().getString("phoneNo"));
                    birthday.setText(task.getResult().getString("dateOfBirth"));
                }
            }
        });

        return view;
    }

    //3
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imageUri = data.getData();

            ImageView imgView = getView().findViewById(R.id.img_prof);
            uploadFile();
            imgView.setImageURI(imageUri);
        }
    }


    private void uploadFile(){
        if(imageUri !=null){
            if(isDetached()){
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
            else{
                StorageReference file = firebaseStorage.child(names +".jpg");
                db.collection("Users").document(names).update("rememberMe",true);
                storageTask=file.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    //3
}