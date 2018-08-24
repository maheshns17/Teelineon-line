package com.example.admin.teelineon_line.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.admin.teelineon_line.R;
import com.example.admin.teelineon_line.api.Api;
import com.example.admin.teelineon_line.api.IPActivity;
import com.example.admin.teelineon_line.api.WebServices;
import com.example.admin.teelineon_line.logic.P;
import com.example.admin.teelineon_line.logic.U;
import com.example.admin.teelineon_line.models.UserLoginInput;
import com.example.admin.teelineon_line.models.UserLoginResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserLoginActivity extends AppCompatActivity {
    @BindView(R.id.edt_username)
    EditText edt_username;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.btn_login)
    ActionProcessButton btn_login;
    @BindView(R.id.btn_register)
    ActionProcessButton btn_register;
    @BindView(R.id.textView)
    TextView textView;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        context = UserLoginActivity.this;
        ButterKnife.bind(this);

        btn_register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, UserRegisterActivity.class);
                startActivity(intent);

            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

        //MODIFIED  BY PRASAD
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, IPActivity.class);
                startActivity(intent);
                return false;
            }
        });

        //TO BE REMOVED
        /*Intent intent = new Intent(context, StrokeInfoActivity.class);
        startActivity(intent);*/

    }

    private void validation() {
        if (!P.isValidEditText(edt_username, "Username")) return;
        if (!P.isValidEditText(edt_password, "Password")) return;
        userLogin();
    }

    private void userLogin() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);



        //PREPARE INPUT/REQUEST PARAMETERS
        UserLoginInput userLoginInput = new UserLoginInput(
                edt_username.getText().toString().trim(),
                edt_password.getText().toString().trim()
        );

        btn_login.setProgress(1);
        btn_login.setEnabled(false);
        P.hideSoftKeyboard(UserLoginActivity.this);


        //CALL NOW
        webServices.userLogin(userLoginInput)
                .enqueue(new Callback<UserLoginResult>() {
                    @Override
                    public void onResponse(Call<UserLoginResult> call, Response<UserLoginResult> response) {
                        if (!P.analyseResponse(response)) {
                            btn_login.setProgress(0);
                            btn_login.setEnabled(true);
                            Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show();
                            return;
                        }
                        UserLoginResult result = response.body();
                        if (result.is_success) {
                            btn_login.setProgress(100);
                            U.userDetails = result.user.get(0);
                            Intent intent = new Intent(context, StrokeInfoActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            btn_login.setProgress(0);
                            btn_login.setEnabled(true);
                            Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        }

                    }


                    @Override
                    public void onFailure(Call<UserLoginResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        btn_login.setProgress(0);
                        btn_login.setEnabled(true);

                    }
                });
    }
}