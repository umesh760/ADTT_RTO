package com.rto_driving_test.Authorization;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rto_driving_test.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utility.BaseActivity;
import utility.ErrorLayout;

public class HomeActivity extends BaseActivity {
    Context context;
    Activity activity;

    @BindView(R.id.bt_fresh)
    Button btfresh;
    @BindView(R.id.bt_retest)
    Button btretest;

    @BindView(R.id.rl_longclick)
    RelativeLayout rl_click;

    @BindView(R.id.ipaddress)
            TextView ipAdd;
    @BindView(R.id.ipaddresstext)
            TextView ipaddresstext;

    Toolbar toolbar;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public static String MY_PREF="ipadd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_diff_applicants);
        ButterKnife.bind(this);
        toolbar=(Toolbar)findViewById(R.id.toolbar_diff);
        toolbar.setTitle("AUTOMATED DRIVING TEST ");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        sp=getApplicationContext().getSharedPreferences(MY_PREF,Context.MODE_PRIVATE);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // setAppBar(getAppString(R.string.select_applic), true);
        initViews();
        context = this;
        activity = this;

        ipAdd.setText(sp.getString("ipaddress",""));



        ipaddresstext.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                startActivity(new Intent(getApplicationContext(),ChangeIPActivity.class));
                finish();
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        callAPI();
    }

   /* @OnLongClick(R.id.rl_longclick)
    public void rl_longclick()
    {
        Toast.makeText(getApplicationContext(),"click long press",Toast.LENGTH_LONG).show();

        startActivity(new Intent(getApplicationContext(),ChangeIPActivity.class));

        return ;


    }*/

    private ErrorLayout errorLayout;

    private void initViews() {
        errorLayout = new ErrorLayout(findViewById(R.id.error_rl));
       /* rl_click.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(getApplicationContext(),"click long press",Toast.LENGTH_LONG).show();

                startActivity(new Intent(getApplicationContext(),ChangeIPActivity.class));
                return true;
            }
        });*/
    }

    @OnClick({R.id.bt_fresh,R.id.bt_retest})/*,R.id.rl_longclick*/
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bt_fresh:

                if(validation()) {
                    Intent in = new Intent(context, ActAppoimentList.class);
                    startActivity(in);
                }
                break;
            case R.id.bt_retest:
                if(validation()) {
                Intent in1 = new Intent(context,RetestActivity.class);
                in1.putExtra("app_id","1");
                startActivity(in1);
                }
                break;

            /*case R.id.rl_longclick:
                Toast.makeText(getApplicationContext(),"click long press",Toast.LENGTH_LONG).show();

                startActivity(new Intent(getApplicationContext(),ChangeIPActivity.class));
                break;*/







        }

    }

    private boolean validation() {

        if(TextUtils.isEmpty(sp.getString("ipaddress",""))) {
            Toast.makeText(getApplicationContext(),"Please Set IP Address",Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
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
            logoutDlg = new Dialog(HomeActivity.this, R.style.MyDialogTheme1);
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
