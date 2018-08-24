package com.example.admin.teelineon_line.api;


import com.example.admin.teelineon_line.models.StrokeinfoInput;
import com.example.admin.teelineon_line.models.StrokeinfoResult;
import com.example.admin.teelineon_line.models.UserLoginInput;
import com.example.admin.teelineon_line.models.UserLoginResult;
import com.example.admin.teelineon_line.models.UserRegisterInput;
import com.example.admin.teelineon_line.models.UserRegisterResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by admin on 17/02/2017.
 */
public interface WebServices {
    @POST("TUserLogin_c/userLogin")
    Call<UserLoginResult> userLogin(@Body UserLoginInput input);

    @POST("TUserRegister_c/userRegister")
    Call<UserRegisterResult> userRegister(@Body UserRegisterInput input);

    @POST("TUploadStrokeInfo_c/userStrokeInfo")
    Call<StrokeinfoResult> userStrokeInfo(@Body StrokeinfoInput input);

}


