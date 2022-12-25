package com.example.midtermproject;;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class HomeScreen extends Fragment implements MyAdapter.ItemClickListener{
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ImageButton img;
    boolean onClicked=false;

    ArrayList <Blog> getList;

    FirebaseFirestore fb=FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_home_screen, container, false);
        recyclerView=view.findViewById(R.id.home_listView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        getList= new ArrayList<Blog>();
        myAdapter = new MyAdapter(getContext(),getList,this::onItemClick);



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
                    getList.add(newBlog);
                    Log.d("array", getList.toString());
                }
                myAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(myAdapter);





        return view;
    }
    public void ImageClick() {


        onClicked =!onClicked ;
        if(onClicked){
            img.setBackgroundResource(R.drawable.dislike);
        }
        else{
            img.setBackgroundResource(R.drawable.like);
        }

    }

    @Override
    public void onItemClick(Blog list) {
        longPharagraphs longS= new longPharagraphs();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.blogDesignFrame,longS);
        fragmentTransaction.commit();
        Bundle bundle = new Bundle();
        bundle.putString("longtitle",list.getTitle());
        bundle.putString("longthoughts",list.getText());
        bundle.putString("longauthor",list.getWriter());
        longS.setArguments(bundle);
    }
}