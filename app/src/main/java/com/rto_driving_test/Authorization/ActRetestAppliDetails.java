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

import com.bumptech.glide.Glide;
import com.rto_driving_test.Functions;
import com.rto_driving_test.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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


String sDiff="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_retest_appli_details);
        ButterKnife.bind(this);
        setAppBar(getAppString(R.string.retest_details), true);
        initViews();
        context = this;
        activity = this;
        checkClick=0;
        sDiff=getIntent().getStringExtra("diff");
        if(sDiff.equals("fresh"))
        {
            btSartTest.setText(getResources().getString(R.string.next));
            btCancel.setVisibility(View.VISIBLE);
            viewDiff.setVisibility(View.VISIBLE);
        }
        else
        {
            btSartTest.setText(getResources().getString(R.string.start_test));
            btCancel.setVisibility(View.GONE);
            viewDiff.setVisibility(View.GONE);
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
                        Intent in = new Intent(context,ActTestReport.class);
                        startActivity(in);
                        finish();

                    }

                }
                else
                {
                    Intent in = new Intent(context,ActTestReport.class);
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

        }
    }

    @Override
    protected void onVideoCaptured(String videoPath) {

    }
    @Override
    protected void onMediaPickCanceled(MediaPicker reqCode) {
    }
}
