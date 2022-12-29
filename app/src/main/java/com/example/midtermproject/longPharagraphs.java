package com.example.midtermproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class longPharagraphs extends Fragment {

    TextView title,thoughts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_long_pharagraphs, container, false);
        title=v.findViewById(R.id.longTitle);
        thoughts=v.findViewById(R.id.longThoughts);
        thoughts.setMovementMethod(new ScrollingMovementMethod());
        SpannableString content = new SpannableString(getArguments().getString("longtitle"));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);


        title.setText(content);
        thoughts.setText(getArguments().getString("longthoughts"));
        getActivity().setTitle(getArguments().getString("longauthor"));


        return v;
    }






}