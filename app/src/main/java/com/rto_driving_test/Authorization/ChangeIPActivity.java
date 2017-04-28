package com.rto_driving_test.Authorization;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rto_driving_test.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.ApiClient;
import retrofit.BaseRequest;
import retrofit.RequestReciever;
import utility.BaseActivity;

public class ChangeIPActivity extends BaseActivity {


    @BindView(R.id.edit_ip)
    EditText ipEdit;
    @BindView(R.id.btn_set)
    Button btn_set;

    String ip;
    BaseRequest baseRequest=null;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public static String MY_PREF="ipadd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_ip);

        ButterKnife.bind(this);

        sp=getApplicationContext().getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        editor=sp.edit();

        ip=ipEdit.getText().toString();

        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               // baseRequest=new BaseRequest(getApplicationContext());
                ApiClient.BASE_URL=null;
                //Config.IP_ADDRESS=ipEdit.getText().toString();

                String s=ipEdit.getText().toString();
                editor.putString("ipaddress",s);
                editor.commit();



                ApiClient.BASE_URL="http://"+sp.getString("ipaddress","")+"/ADTT_SEVICE/";
//                BaseRequest baseRequest_list=new BaseRequest(ChangeIPActivity.this);
              //  callApi();
                Toast.makeText(getApplicationContext(),"New IP configuration set up"+"\n"+ApiClient.BASE_URL,Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });

    }
//    BaseRequest baseRequest_list=null;
    private void callApi() {

        BaseRequest  baseRequest_list=new BaseRequest(ChangeIPActivity.this);
        baseRequest_list.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {

                Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {

                Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_LONG).show();

            }
        });


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            baseRequest_list.callAPIGET(1,new ArrayMap<String, String>(),"Get_Applicant_list.svc/Get_Applicant/");
        }

    }
}
