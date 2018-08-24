package com.example.admin.teelineon_line.models;

/**
 * Created by Admin on 1/9/2018.
 */

public class StrokeinfoInput {

    public String user_id   ;
    public String Wi_Writer_name;
    public String Wi_age ;
    public String Wi_hand_specification;
    public String Wi_qualification;
    public String Wi_familier_type ;
    public String Wi_alphabet_type;
    public String Wi_num_of_Strokes;
    public String Wi_x_axis_pixels;
    public String Wi_y_axis_pixels;
    public String Wi_gender;

    /*====CONSTRUCTOR====ALT + INSERT*/

    public StrokeinfoInput(String user_id, String wi_Writer_name, String wi_age, String wi_hand_specification, String wi_qualification, String wi_familier_type, String wi_alphabet_type, String wi_num_of_Strokes, String wi_x_axis_pixels, String wi_y_axis_pixels, String wi_gender) {
        this.user_id = user_id;
        Wi_Writer_name = wi_Writer_name;
        Wi_age = wi_age;
        Wi_hand_specification = wi_hand_specification;
        Wi_qualification = wi_qualification;
        Wi_familier_type = wi_familier_type;
        Wi_alphabet_type = wi_alphabet_type;
        Wi_num_of_Strokes = wi_num_of_Strokes;
        Wi_x_axis_pixels = wi_x_axis_pixels;
        Wi_y_axis_pixels = wi_y_axis_pixels;
        Wi_gender = wi_gender;
    }
}
