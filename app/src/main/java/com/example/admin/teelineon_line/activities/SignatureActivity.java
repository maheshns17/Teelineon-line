package com.example.admin.teelineon_line.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.admin.teelineon_line.R;
import com.example.admin.teelineon_line.logic.P;
import com.example.admin.teelineon_line.logic.U;
import com.simplify.ink.InkView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Prasad K P on 11/5/2015.
 */
public class SignatureActivity extends AppCompatActivity {

    @BindView(R.id.paintView)
    InkView drawableView;

    @BindView(R.id.btn_ok)
    ActionProcessButton btn_ok;

    @BindView(R.id.btn_clear)
    ActionProcessButton btn_clear;

    @BindView(R.id.img_signature)
    ImageView img_signature;

    @BindView(R.id.txt_info)
    TextView txt_info;
    private int strokes = 0;
    private int x_axis = 0;
    private int y_axis = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        ButterKnife.bind(this);
        U.STROKES=0;
        U.X_AXIS=0;
        U.Y_AXIS=0;


        drawableView.setColor(getResources().getColor(android.R.color.black));
        drawableView.setMinStrokeWidth(1.5f);
        drawableView.setMaxStrokeWidth(6f);
        btn_ok.setMode(ActionProcessButton.Mode.ENDLESS);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strokes != 0) {
                    U.STROKES = strokes - 1;
                    x_axis = U.getXAxisValue();
                    y_axis = U.getYAxisValue();
                    U.X_AXIS = x_axis;
                    U.Y_AXIS = y_axis;
                }
                btn_ok.setProgress(1);
                Intent intent = new Intent();
                intent.putExtra("signature", P.getByteArrayFromBitmap(drawableView.getBitmap()));
                setResult(RESULT_OK, intent);
                btn_ok.setProgress(100);
                finish();

            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawableView.clear();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onUserLeaveHint() {
        P.LogD("SIGNATURE onUserLeaveHint");
    }

    @Override
    public void onUserInteraction() {
        P.LogD("SIGNATURE OnUserIntercation");
        strokes = strokes + 1;//INCREMENT STROKES VALUE FOR EVERY INTERACTION
        txt_info.setText("Strokes : " + strokes);
    }
}
