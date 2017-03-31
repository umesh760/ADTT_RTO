package com.rto_driving_test.Authorization;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rto_driving_test.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utility.BaseActivity;
import utility.ErrorLayout;

public class ActResult extends BaseActivity {
    Context context;
    Activity activity;

    @BindView(R.id.btn_submit_detail)
    Button btTwoWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_result_new);
        ButterKnife.bind(this);
        setAppBar(getAppString(R.string.result), true);
        initViews();
        context = this;
        activity = this;

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
                Intent in = new Intent(context,ActAppoimentList.class);
                    startActivity(in);
                finishAllActivities();
                break;



        }
    }


}
