package com.rto_driving_test.Authorization;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.rto_driving_test.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utility.BaseActivity;
import utility.ErrorLayout;

public class ActDiffApplicants extends BaseActivity {
    Context context;
    Activity activity;

    @BindView(R.id.bt_fresh)
    Button btfresh;
    @BindView(R.id.bt_retest)
    Button btretest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_diff_applicants);
        ButterKnife.bind(this);
        setAppBar(getAppString(R.string.select_applic), true);
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

    @OnClick({R.id.bt_fresh,R.id.bt_retest})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bt_fresh:
                Intent in = new Intent(context,ActAppoimentList.class);
                    startActivity(in);
                break;
            case R.id.bt_retest:
                Intent in1 = new Intent(context,ActReTestLogin.class);
             in1.putExtra("app_id","1");
                startActivity(in1);
                break;


        }
    }


        @Override
    public void onBackPressed() {
            try {
                logout("Wish to exit ?", 2);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
    Dialog logoutDlg = null;

    public void logout(String mes, final int dif) {
        try {
            if (logoutDlg != null) {
                logoutDlg.cancel();
            }
            logoutDlg = new Dialog(ActDiffApplicants.this, R.style.MyDialogTheme1);
            logoutDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
            logoutDlg.setContentView(R.layout.logout_app);
            logoutDlg.setCanceledOnTouchOutside(false);
            TextView tvTitle = (TextView) logoutDlg.findViewById(R.id.exittext);
            tvTitle.setText(mes);
            Button no = (Button) logoutDlg.findViewById(R.id.No);
            Button yes = (Button) logoutDlg.findViewById(R.id.yes);
            Button divi = (Button) logoutDlg.findViewById(R.id.divide_button);
            logoutDlg.show();
            if (dif == 3) {
//                divi.setVisibility(View.GONE);
//                yes.setVisibility(View.GONE);
                no.setText("Ok");
            } else {
                divi.setVisibility(View.VISIBLE);
                yes.setVisibility(View.VISIBLE);
                no.setText("Cancel");
            }
            yes.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        if (dif == 2) {
                            finish();
                        }
                        if (logoutDlg != null) {
                            logoutDlg.dismiss();
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            });
            no.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (logoutDlg != null) {
                        logoutDlg.cancel();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
