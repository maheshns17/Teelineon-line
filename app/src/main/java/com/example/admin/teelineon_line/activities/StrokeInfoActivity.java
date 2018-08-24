package com.example.admin.teelineon_line.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.admin.teelineon_line.R;
import com.example.admin.teelineon_line.api.Api;
import com.example.admin.teelineon_line.api.WebServices;
import com.example.admin.teelineon_line.logic.P;
import com.example.admin.teelineon_line.logic.U;
import com.example.admin.teelineon_line.models.StrokeinfoInput;
import com.example.admin.teelineon_line.models.StrokeinfoResult;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class StrokeInfoActivity extends AppCompatActivity {
    @BindView(R.id.img_signature)
    ImageView img_signature;
    @BindView(R.id.sp_age_group)
    SearchableSpinner sp_age_group;
    @BindView(R.id.sp_gender)
    SearchableSpinner sp_gender;
    @BindView(R.id.sp_hand_specification)
    SearchableSpinner sp_hand_specification;
    @BindView(R.id.sp_qualification)
    SearchableSpinner sp_qualification;
    @BindView(R.id.sp_familarity)
    SearchableSpinner sp_familarity;
    @BindView(R.id.sp_alphabet)
    SearchableSpinner sp_alphabet;

    @BindView(R.id.btn_Submit)
    ActionProcessButton btn_Submit;

    @BindView(R.id.edt_writer)
    EditText edt_writer;
    @BindView(R.id.edt_writerid)
    EditText edt_writerid;


    private Context context;
    private String str_signature = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stroke_info);
        ButterKnife.bind(this);
        context = StrokeInfoActivity.this;

        img_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignatureActivity.class);
                startActivityForResult(intent, U.SIGNATURE);
            }
        });

        edt_writerid.setText(U.userDetails.user_id);

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();

            }
        });

        setSpinner();
    }


    private void validation() {

        if (!P.isValidEditText(edt_writer, "writer")) return;
        if (!P.isValidEditText(edt_writerid, "writer id")) return;


        if (sp_age_group.getSelectedItemPosition() == 0) {
            Toast.makeText(context, "Please Select Age Group", Toast.LENGTH_LONG).show();
            return;
        }

        if (sp_gender.getSelectedItemPosition() == 0) {
            Toast.makeText(context, "Please Select Gender", Toast.LENGTH_LONG).show();
            return;
        }
        if (sp_hand_specification.getSelectedItemPosition() == 0) {
            Toast.makeText(context, "Please Select Hand Specification", Toast.LENGTH_LONG).show();
            return;
        }

        if (sp_qualification.getSelectedItemPosition() == 0) {
            Toast.makeText(context, "Please Select Qualification", Toast.LENGTH_LONG).show();
            return;
        }

        if (sp_familarity.getSelectedItemPosition() == 0) {
            Toast.makeText(context, "Please Select Familarity", Toast.LENGTH_LONG).show();
            return;
        }
        if (sp_alphabet.getSelectedItemPosition() == 0) {
            Toast.makeText(context, "Please Select Alphabet", Toast.LENGTH_LONG).show();
            return;
        }

        userStrokeInfo();
    }


    private void setSpinner() {
        P.setSpinnerAdapter(context, sp_age_group, getResources().getStringArray(R.array.age_group));
        P.setSpinnerAdapter(context, sp_gender, getResources().getStringArray(R.array.gender));
        P.setSpinnerAdapter(context, sp_hand_specification, getResources().getStringArray(R.array.hand_specification));
        P.setSpinnerAdapter(context, sp_qualification, getResources().getStringArray(R.array.qualification));
        P.setSpinnerAdapter(context, sp_familarity, getResources().getStringArray(R.array.familarity));
        P.setSpinnerAdapter(context, sp_alphabet, getResources().getStringArray(R.array.alphabet));

    }


    private void userStrokeInfo() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        StrokeinfoInput StrokeinfoInput = new StrokeinfoInput(
                U.userDetails.user_id,
                edt_writer.getText().toString().trim(),
                sp_age_group.getSelectedItem().toString(),
                sp_hand_specification.getSelectedItem().toString(),
                sp_qualification.getSelectedItem().toString(),
                sp_familarity.getSelectedItem().toString(),
                sp_alphabet.getSelectedItem().toString(),
                U.STROKES + "",
                U.X_AXIS + "",
                U.Y_AXIS + "",
                sp_gender.getSelectedItem().toString()
        );

        btn_Submit.setProgress(1);
        btn_Submit.setEnabled(false);

        //CALL NOW
        webServices.userStrokeInfo(StrokeinfoInput)
                .enqueue(new Callback<StrokeinfoResult>() {
                    @Override
                    public void onResponse(Call<StrokeinfoResult> call, Response<StrokeinfoResult> response) {
                        if (!P.analyseResponse(response)) {
                            btn_Submit.setProgress(0);
                            btn_Submit.setEnabled(true);
                            Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show();
                            return;
                        }
                        StrokeinfoResult result = response.body();
                        if (result.is_success) {
                            btn_Submit.setProgress(100);
                            btn_Submit.setEnabled(true);
                            Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                            sp_alphabet.setSelection(0);
                        } else {
                            btn_Submit.setProgress(0);
                            btn_Submit.setEnabled(true);
                            Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        }

                    }


                    @Override
                    public void onFailure(Call<StrokeinfoResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        btn_Submit.setProgress(0);
                        btn_Submit.setEnabled(true);

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case U.SIGNATURE:
                if (resultCode != RESULT_OK) {
                    return;
                }
                Bitmap mBitmapSignature = P.getBitmapFromByteArray(data.getByteArrayExtra("signature"));
                //mBitmapSignature = XY.getResizedBitmap(mBitmapSignature, U.SIGNATURE_QTY);
                img_signature.setImageBitmap(mBitmapSignature);
                str_signature = P.BitmapToString(mBitmapSignature);

                /*====DIRECTLY USE U.STROKES, U.X_AXIS, U.Y_AXIS WHILE SENDING INPUT TO WEBSERVICE====*/


                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
