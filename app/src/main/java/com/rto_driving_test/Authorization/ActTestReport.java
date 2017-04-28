package com.rto_driving_test.Authorization;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rto_driving_test.Functions;
import com.rto_driving_test.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import retrofit.BaseRequest;
import retrofit.RequestReciever;
import utility.Base64Decode;
import utility.BaseActivity;
import utility.Config;
import utility.ErrorLayout;
import utility.ResizeImage;

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
    @BindView(R.id.bt_start)
    Button btStartTest;
    @BindView(R.id.bt_pending)
    Button btnPending;
    @BindView(R.id.tv_start)
    TextView timer;

    MultipartBody.Part body;

String imagepath="";

    String type_vehical;

    JSONObject jsonObject=null;
    String typetest="";


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



        Intent i=new Intent();
        typetest=getIntent().getStringExtra("type");
        if(typetest.equalsIgnoreCase("fresh"))
        {

            try {
                jsonObject = new JSONObject(getIntent().getStringExtra("dataobject"));
            }
            catch (Exception e)
            {
                System.out.println("Exception"+e.toString());
            }

        }



        type_vehical=getIntent().getStringExtra("vehical");

        Log.e("VEHICAL>>>>>>>>",type_vehical);
        initViews();
        btReTest.setVisibility(View.GONE);


      /*  String s=Config.IMAGE_PATH;
        encodeImage(s);

       ResizeImage resize= new ResizeImage(ActTestReport.this);


        //resize.compressImage(s);
        String comPath= resize.compressImage(s);
        Bitmap  photo = BitmapFactory.decodeFile(comPath);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 10, stream);
        byte[] byteArray = stream.toByteArray();
        imagepath = Base64Decode.encodeBytes(byteArray);*/


    }





      /*  File file = new File(Config.IMAGE_PATH);
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image*//*"),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
         body =
                MultipartBody.Part.createFormData("imgup", file.getName(), requestFile);
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, "1");*/

       // baseRequest.callAPIPostImage(1,body,description);


        private String encodeImage(String path)
        {
            File imagefile = new File(path);
            FileInputStream fis = null;
            try{
                fis = new FileInputStream(imagefile);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
            Bitmap bm = BitmapFactory.decodeStream(fis);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] b = baos.toByteArray();
            String encImage = Base64.encodeToString(b, Base64.DEFAULT);
            //Base64.de
            imagepath=encImage;
            return encImage;

        }



    private ErrorLayout errorLayout;

    private void initViews() {
        errorLayout = new ErrorLayout(findViewById(R.id.error_rl));
    }

    @OnClick({R.id.bt_submit,R.id.bt_restart,R.id.bt_start,R.id.bt_pending})//R.id.login_btn
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bt_start:
                btStartTest.setVisibility(View.GONE);
                btReTest.setVisibility(View.VISIBLE);
                conddowntimer();
                if(type_vehical.equalsIgnoreCase("twowheeler"))
                {
                    callApiforTW();
                }
                else if(type_vehical.equalsIgnoreCase("fourwheeler"))
                {
                    callApiforFW();

                }

                break;


            case R.id.bt_restart:

                Intent in = new Intent(context,HomeActivity.class);
                startActivity(in);
                finishAllActivities();
                break;
            case R.id.bt_submit:

                /*Intent in1 = new Intent(context, ResultActivity.class);
                startActivity(in1);*/

                /*callApi();*/


                if(typetest.equalsIgnoreCase("fresh")) {
                    Intent in1 = new Intent(context, ResultActivity.class);
                    in1.putExtra("dataobject", jsonObject.toString());

                    startActivity(in1);
                }
                else if(typetest.equalsIgnoreCase("retest")) {

                    Intent in1 = new Intent(context, ResultActivity.class);
                    in1.putExtra("dataobject","");
                    startActivity(in1);
                }
                else if(typetest.equalsIgnoreCase("pending"))
                {
                    Intent in1 = new Intent(context, ResultActivity.class);
                    in1.putExtra("dataobject","");
                    startActivity(in1);

                }



                break;

               case R.id.bt_pending:
                Intent intent =new Intent(getApplicationContext(),PendingTest.class);
                startActivity(intent);

                break;





        }
    }
BaseRequest baseRequestFW;
    private void callApiforFW() {


        baseRequestFW=new BaseRequest(this);
        baseRequestFW.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {



                if(!TextUtils.isEmpty(object.toString()))
                {

                    JSONArray data=(JSONArray)object;

                    try {
                        JSONObject jsonObject=data.getJSONObject(0);
                        String message=jsonObject.optString("Message");

                        Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_LONG).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


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

        JsonObject jsonObject=Functions.getClient().getJsonMapObject(
                "DriverNo", String.valueOf(Config.DRIVER_NUMBER),
                "LL_No",Config.LICENCE_NUMBER,"Receipt_No",
                Config.RECEIPT_NUMBER,"Reference_no",Config.REF_NUMBER,
                "Test_type",Config.TESTTYPE,"gRTOCODE",Config.RTO_CODE,
                    "DashBoard_Cam","192.168.10.10");
        baseRequestFW.callAPIPost(1,jsonObject,"Get_Applicant_list.svc/FW_Test/Get_Fw_request");

    }


    BaseRequest baseRequestTW;

    private void callApiforTW() {

        baseRequestTW=new BaseRequest(this);

        baseRequestTW.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {

                if(!TextUtils.isEmpty(object.toString()))
                {

                    JSONArray data=(JSONArray)object;

                    try {
                        JSONObject jsonObject=data.getJSONObject(0);
                        String message=jsonObject.optString("Message");

                        Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_LONG).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


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

       // String url="Get_Applicant_list.svc/TW_Test/"+Config.DRIVER_NUMBER +"/"+ Config.RTO_CODE+"/"+Config.RECEIPT_NUMBER+"/"+Config.LICENCE_NUMBER+"/"+Config.REF_NUMBER+"/"+Config.TESTTYPE;


        JsonObject jsonObject=Functions.getClient().getJsonMapObject(
                "DriverNo", String.valueOf(Config.DRIVER_NUMBER),
                "LL_No",Config.LICENCE_NUMBER,
                "Receipt_No",Config.RECEIPT_NUMBER,
                "Reference_no",Config.REF_NUMBER,
                "Test_type",Config.TESTTYPE,"gRTOCODE",Config.RTO_CODE);
        baseRequestTW.callAPIPost(1,jsonObject,"Get_Applicant_list.svc/TW_Test/Get_Tw_request");

    }

    private void conddowntimer() {

        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {

                int seconds= (int) TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);


               timer.setText("0"+minutes+":"+seconds);
               /* timer.setText(""+String.format("%d min, %d sec",TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)));*/
            }

            public void onFinish() {
                timer.setText("00:00");
            }
        }.start();

    }

    BaseRequest baseRequest;

    private void callApi() {


        baseRequest=new BaseRequest(this);

        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {

                Toast.makeText(getApplicationContext(),object.toString(),Toast.LENGTH_LONG).show();

                if(object!=null) {

                    try {
                        JSONArray data=(JSONArray)object;

                        JSONObject jsonObject=data.getJSONObject(0);

                        Intent in1 = new Intent(context, ResultActivity.class);
                        in1.putExtra("dataobject",jsonObject.toString());

                        startActivity(in1);



                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"Alredy submit",Toast.LENGTH_LONG).show();
                }

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

        JsonObject jsonObject= Functions.getClient().getJsonMapObject(
                "RTOCODE",Config.RTO_CODE,
                "DriverNo", String.valueOf(Config.DRIVER_NUMBER),
                "Receipt_No",Config.RECEIPT_NUMBER,
                "Ref_Number",Config.REF_NUMBER,
                "LL_NO",Config.LICENCE_NUMBER,
                "picture", imagepath);


baseRequest.callAPIPost(1,jsonObject,"Get_Applicant_list.svc/Get_SaveApplicantBio/Get_BioInfo");


    }



}

