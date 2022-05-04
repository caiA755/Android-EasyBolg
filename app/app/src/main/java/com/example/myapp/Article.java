package com.example.myapp;

public class Article {

    private int AID;
    private String Content;
    private String  Time;
    private String Title;
    private String Author;
    private String Flag;
    private String  PostImg;
    public void setAID(int AID) {
        this.AID = AID;
    }
    public int getAID() {
        return AID;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }
    public String getContent() {
        return Content;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }
    public String getTime() {
        return Time;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }
    public String getTitle() {
        return Title;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }
    public String getAuthor() {
        return Author;
    }

    public void setFlag(String Flag) {
        this.Flag = Flag;
    }
    public String getFlag() {
        return Flag;
    }

    public void setPostImg(String  PostImg) {
        this.PostImg = PostImg;
    }
    public String  getPostImg() {
        return PostImg;
    }

}

