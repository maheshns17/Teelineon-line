package com.example.admin.teelineon_line.models;

/**
 * Created by Admin on 1/7/2018.
 */
public class UserRegisterInput {
    public String user_username;
    public String user_password;
    public String user_full_name;
    public String user_mobile_number;
    public String user_email;


    public UserRegisterInput(String user_username, String user_password, String user_full_name, String user_mobile_number, String user_email) {
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_full_name = user_full_name;
        this.user_mobile_number = user_mobile_number;
        this.user_email = user_email;
    }
}
