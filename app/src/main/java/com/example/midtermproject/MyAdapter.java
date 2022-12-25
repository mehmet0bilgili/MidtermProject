package com.example.midtermproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Blog> list;
    public ItemClickListener clickListener;


    public MyAdapter(Context context, ArrayList<Blog> list,ItemClickListener clickListener) {
        this.context = context;
        this.list = list;
        this.clickListener= clickListener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.listview_blog,parent,false);

        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        Blog blog =list.get(position);
        holder.title.setText(blog.getTitle());
        holder.thoughts.setText(blog.getText());
        holder.author.setText(blog.getWriter());
        holder.date.setText(blog.getDate());
        holder.thoughts.setMovementMethod(new ScrollingMovementMethod());
        db.collection("Users").document(blog.getWriter()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().getBoolean("rememberMe")== true){
                        try {
                            File localFile = File.createTempFile("temp",".jpg");
                            StorageReference st = FirebaseStorage.getInstance().getReference("uploads/"+blog.getWriter()+".jpg");
                            st.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    holder.imageView.setImageBitmap(bitmap);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        holder.thoughts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(list.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView author,date,thoughts,title;
        ImageView imageView;

        public  MyViewHolder(@NonNull View itemView){
            super(itemView);
            author=itemView.findViewById(R.id.author);
            date=itemView.findViewById(R.id.date);
            thoughts=itemView.findViewById(R.id.home_thought);
            title=itemView.findViewById(R.id.home_title);
            imageView=itemView.findViewById(R.id.prof_image);


        }

    }
    public  interface ItemClickListener {
        public void onItemClick(Blog list);
    }
}
