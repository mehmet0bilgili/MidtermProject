package com.example.midtermproject;

public class Upload {
    private String name;
    private String imageUri;

    public Upload(String name, String imageUri) {
        if(name.trim().equals("")){
            name="No name";
        }
        this.name = name;
        this.imageUri = imageUri;
    }

    public Upload(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
