package com.rto_driving_test.Authorization;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rto_driving_test.Models.AppointmentModel;
import com.rto_driving_test.R;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;
import retrofit.ApiClient;
import retrofit.BaseRequest;
import retrofit.RequestReciever;
import utility.Config;

public class ChangeIPActivity extends AppCompatActivity {


    @BindView(R.id.edit_ip)
    EditText ipEdit;
    @BindView(R.id.btn_set)
    Button btn_set;

    String ip;
    BaseRequest baseRequest=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_ip);

        ButterKnife.bind(this);

        ip=ipEdit.getText().toString();

        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               // baseRequest=new BaseRequest(getApplicationContext());
                ApiClient.BASE_URL=null;
                //Config.IP_ADDRESS=ipEdit.getText().toString();
                ApiClient.BASE_URL="http://"+ipEdit.getText().toString()+"/ADTT_SEVICE/";
//                BaseRequest baseRequest_list=new BaseRequest(ChangeIPActivity.this);
              //  callApi();
                Toast.makeText(getApplicationContext(),"New IP configuration set up"+"\n"+ApiClient.BASE_URL,Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),ActDiffApplicants.class));
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
