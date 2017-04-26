package com.rto_driving_test.Authorization;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rto_driving_test.Functions;
import com.rto_driving_test.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utility.Config;
import utility.ErrorLayout;
import utility.MyImage;

public class ActRetestAppliDetails extends MediaPickerActivity {
    Context context;
    Activity activity;

    @BindView(R.id.btn_start_test)
    Button btSartTest;
    @BindView(R.id.btn_cancel_retest)
    Button btCancel;
    @BindView(R.id.view_diff)
    View viewDiff;
    @BindView(R.id.iv_date)
    ImageView img;


    @BindView(R.id.tv_name)
    TextView nametxt;
    @BindView(R.id.tv_id)
    TextView idtxt;
    @BindView(R.id.tv_ap_no)
    TextView ap_no_txt;
    @BindView(R.id.tv_nm)
    TextView nm_tv_txt;
    @BindView(R.id.tv_fath_nm)
    TextView fath_nm_txt;
    @BindView(R.id.tv_dob)
    TextView tv_dob_txt;
    @BindView(R.id.tv_dl_no)
    TextView tv_dl_txt;
    @BindView(R.id.tv_date)
    TextView tv_date_txt;
    @BindView(R.id.tv_time)
    TextView tv_time_txt;
    /*@BindView(R.id.tv_vehicle_class)
    TextView vehical_txt;*/









    String sDiff="";
    String nameApp="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_retest_appli_details);
        ButterKnife.bind(this);
        sDiff=getIntent().getStringExtra("diff");
        nameApp=getIntent().getStringExtra("name");
        if(sDiff.equals("fresh"))
        {

            setAppBar("CANDIDATE DETAIL (FRESH)", true);
        }
        else {
            setAppBar("CANDIDATE DETAIL (RETEST)", true);
        }


//        setAppBar(getAppString(R.string.retest_details), true);
//        setAppBar(getAppString(R.string.retest_details), true);

        //setAppBar(name,true);
        initViews();
        context = this;
        activity = this;
        checkClick=0;

        //Toast.makeText(getApplicationContext(),""+nameApp,Toast.LENGTH_LONG).show();

        setTextValue();

        if(sDiff.equals("fresh"))
        {
            btSartTest.setText(getResources().getString(R.string.next));
            btCancel.setVisibility(View.VISIBLE);
            viewDiff.setVisibility(View.VISIBLE);
        }
        else
        {
          img.setImageBitmap(Config.APPLICANT_PIC_BASE64);
            btSartTest.setText(getResources().getString(R.string.start_test));
            btCancel.setVisibility(View.GONE);
            viewDiff.setVisibility(View.GONE);
        }
    }

    private void setTextValue() {


        nametxt.setText(Config.APPLICANT_FIRST_NAME+"\t"+Config.APPLICANT_LAST_NAME);
        idtxt.setText(Config.REF_NUMBER);
        ap_no_txt.setText(Config.RECEIPT_NUMBER);
        nm_tv_txt.setText(Config.APPLICANT_FIRST_NAME+"\t"+Config.APPLICANT_LAST_NAME);
        fath_nm_txt.setText(Config.SOWODO);
        tv_dob_txt.setText(Config.DOB);
        tv_dl_txt.setText(Config.LICENCE_NUMBER);
        tv_dob_txt.setText(Config.DOB);
      //  tv_date_txt.setText(Config.APPOINTMENT_DATE);


        String dnt=Config.APPOINTMENT_DATE;

       /* if(Config.APPOINTMENT_DATE!=null || Config.APPOINTMENT_DATE !="") {
            String items[] = dnt.split(" ");
            tv_date_txt.setText(items[0]);
            if(items[1]!=null && items[2]!=null) {
                tv_time_txt.setText(items[1] + items[2]);
            }
        }
        else if(Config.APPOINTMENT_DATE=="" || Config.APPOINTMENT_DATE==null){

            tv_date_txt.setText("Not Updated");
            tv_time_txt.setText("Not Updated");

        }*/








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

    @OnClick({R.id.btn_start_test,R.id.btn_cancel_retest})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_start_test:

                if(sDiff.equals("fresh"))
                {
                    if(checkClick==0)
                    {
                        PickMedia(MediaPicker.Camera);
                    }
                    else
                    {
                        Intent in = new Intent(context,VehicalSelectionActivity.class);
                        startActivity(in);
                        finish();

                    }

                }
                else
                {
                    Intent in = new Intent(context,VehicalSelectionActivity.class);/*ActTestReport*/
                    startActivity(in);
                    finish();
                }
                break;
            case R.id.btn_cancel_retest:

                finish();

                break;


        }
    }
int checkClick=0;
    @Override
    protected void onSingleImageSelected(int starterCode, File fileUri, String imagPath, Bitmap bitmap, String compressPath) {
        if (!TextUtils.isEmpty(compressPath)) {
            String path=compressPath; //camera
            checkClick=1;
            btSartTest.setText(getResources().getString(R.string.start_test));


            MyImage.displayImage(this,path,img);
            Config.IMAGE_PATH=path;


        }
    }

    @Override
    protected void onVideoCaptured(String videoPath) {

    }
    @Override
    protected void onMediaPickCanceled(MediaPicker reqCode) {
    }
}
