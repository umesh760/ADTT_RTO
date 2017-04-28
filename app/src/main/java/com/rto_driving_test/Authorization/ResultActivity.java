package com.rto_driving_test.Authorization;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rto_driving_test.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utility.BaseActivity;
import utility.Config;
import utility.ErrorLayout;

public class ResultActivity extends BaseActivity {
    Context context;
    Activity activity;

    @BindView(R.id.btn_submit_detail)
    Button btTwoWheel;

    @BindView(R.id.tv_ap_no)
    TextView type;
    @BindView(R.id.tv_nm)
    TextView trackpath;
    @BindView(R.id.tv_fath_nm)
    TextView trackpathsecond;
    @BindView(R.id.tv_dob)
    TextView allotedtime;
    @BindView(R.id.tv_dl_no)
    TextView startTime;
    @BindView(R.id.tv_date)
    TextView endTime;
    @BindView(R.id.tv_time)
    TextView duration;
    @BindView(R.id.tv_vehicle_status)
    TextView status;
    @BindView(R.id.tv_name)
    TextView applicantName;
    @BindView(R.id.tv_id)
    TextView tv_id;
    @BindView(R.id.applicant_img)
    ImageView app_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_result_new);
        ButterKnife.bind(this);
        setAppBar(getAppString(R.string.result), true);
        initViews();
        context = this;
        activity = this;

        Intent i=new Intent();
        try {
            JSONObject jsonObject=new JSONObject(getIntent().getStringExtra("dataobject"));

            applicantName.setText(jsonObject.getString("Applicant_Name"));
            tv_id.setText(jsonObject.getString("Reference_Number"));
            status.setText(jsonObject.getString("TEST_TYPE"));

            String img=jsonObject.getString("APPLICANT_PIC");

            byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
           // app_pic.setImageBitmap(decodedByte);
            app_pic.setImageBitmap(Config.APPLICANT_PIC_BASE64);




            //Toast.makeText(getApplicationContext(),""+jsonObject,Toast.LENGTH_LONG).show();
            System.out.println("$$$$$$$$$$$$$"+jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
//        callAPI();
    }

    private ErrorLayout errorLayout;

    private void initViews() {
        errorLayout = new ErrorLayout(findViewById(R.id.error_rl));
    }

    @OnClick({R.id.btn_submit_detail})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_submit_detail:
                Intent in = new Intent(context,HomeActivity.class);
                    startActivity(in);
                finishAllActivities();
                break;



        }
    }


}
