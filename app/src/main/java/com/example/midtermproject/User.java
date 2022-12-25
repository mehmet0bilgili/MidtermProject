package com.example.midtermproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String username, password, name, surname, dateOfBirth, education, phoneNo, email, sex;
    private boolean rememberMe;
    private ArrayList<Blog> userBlog;



    public User(String username, String password, String name, String surname, String dateOfBirth, String education, String phoneNo, String email, String sex, ArrayList<Blog> userBlog,
                boolean rememberMe) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.education = education;
        this.phoneNo = phoneNo;
        this.email = email;
        this.sex = sex;
        this.userBlog=userBlog;
        this.rememberMe=rememberMe;
    }






    public ArrayList<Blog> getUserBlog() {
        return userBlog;
    }

    public void setUserBlog(ArrayList<Blog> userBlog) {
        this.userBlog = userBlog;
    }
    public void addBlog(Blog blog){
        userBlog.add(blog);
    }

    public String getSex() {
        return sex;
    }


    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }}
