package com.example.myapp;

public class  User{
    private  String Password;
    private  String Username;
    private String Email;
    public void setPassword(String Password) {
        this.Password = Password;
    }
    public String getPassword() {
        return Password;
    }
    public void setUsername(String Username) {
        this.Username = Username;
    }
    public String getUsername() {
        return Username;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public String getEmail() {
        return Email;
    }
}