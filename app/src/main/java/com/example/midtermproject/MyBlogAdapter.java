package com.example.midtermproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyBlogAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Blog> list;

    public MyBlogAdapter(Context context, ArrayList<Blog> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.listview_blog,parent,false);
        return  new MyAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Blog blog =list.get(position);
        holder.title.setText(blog.getTitle());
        holder.thoughts.setText(blog.getText());
        holder.author.setText(blog.getWriter());
        holder.date.setText(blog.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView author,date,thoughts,title;


        public  MyViewHolder(@NonNull View itemView){
            super(itemView);
            author=itemView.findViewById(R.id.author);
            date=itemView.findViewById(R.id.date);
            thoughts=itemView.findViewById(R.id.home_thought);
            title=itemView.findViewById(R.id.home_title);
        }
    }
}


