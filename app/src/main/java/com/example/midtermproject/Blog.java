package com.example.midtermproject;

import android.os.Parcel;
import android.os.Parcelable;

public class Blog  {
    private  int id;
    private String writer;
    private int like, dislike;
    private  String uniqueID;
    private String date, text, title;
    public Blog(){

    }
    public Blog(int id, int like, int dislike, String  uniqueID, String date, String text, String title,String writer) {
        this.id=id;
        this.like = like;
        this.dislike = dislike;
        this.uniqueID = uniqueID;
        this.date = date;
        this.text = text;
        this.title = title;
        this.writer=writer;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
