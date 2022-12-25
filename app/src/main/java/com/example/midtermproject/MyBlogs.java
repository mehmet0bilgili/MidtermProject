package com.example.midtermproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MyBlogs extends Fragment {

    RecyclerView recyclerView;
    MyBlogAdapter myAdapter;
    ImageButton img;
    ArrayList<Blog> myList;

    FirebaseFirestore fb=FirebaseFirestore.getInstance();
    String names;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_blogs, container, false);
        recyclerView=view.findViewById(R.id.myBlogListView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        myList= new ArrayList<Blog>();
        names=getArguments().getString("bloguser");
        myAdapter = new MyBlogAdapter(getContext(),myList);
        getActivity().setTitle("My Blogs");


        fb.collection("Blog").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error !=null){
                    Log.e("eror","eror retrieving info",error);
                    return;
                }
                for (QueryDocumentSnapshot doc : value){
                    Log.d("values", doc.getData().toString());
                    Blog newBlog = doc.toObject(Blog.class);
                    if(newBlog.getWriter().equals(names)){
                        myList.add(newBlog);
                        Log.d("array",myList.toString());
                    }
                }
                myAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(myAdapter);

        return view;
    }
}