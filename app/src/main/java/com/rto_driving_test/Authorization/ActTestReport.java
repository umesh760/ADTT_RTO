package com.rto_driving_test.Authorization;

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

public class ActTestReport extends BaseActivity {

    Context context;
//    @BindView(R.id.toolbar_default)
//    Toolbar tootlbar;
//    @BindView(R.id.tv_title)
//    TextView tvTitle;

    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.bt_restart)
    Button btReTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.act_test_report);
        context = this;
        ButterKnife.bind(this);
        setAppBar(getAppString(R.string.test_report), true);
//        tootlbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
//        tvTitle.setText(getAppString(R.string.result));
//        tootlbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                startActivity(new Intent(ActShowRoute.this,LoginActivity.class));
//                finish();
//            }
//        });
        initViews();
    }

    private ErrorLayout errorLayout;

    private void initViews() {
        errorLayout = new ErrorLayout(findViewById(R.id.error_rl));
    }

    @OnClick({R.id.bt_submit,R.id.bt_restart})//R.id.login_btn
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_restart:
                Intent in = new Intent(context,ActDiffApplicants.class);
                startActivity(in);
                finishAllActivities();
                break;
            case R.id.bt_submit:
                Intent in1 = new Intent(context,ActResult.class);
                startActivity(in1);
                break;


        }
    }

}
