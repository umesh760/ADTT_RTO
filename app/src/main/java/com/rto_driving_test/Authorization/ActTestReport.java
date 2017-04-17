package com.rto_driving_test.Authorization;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit.BaseRequest;
import retrofit.RequestReciever;
import utility.Base64Decode;
import utility.BaseActivity;
import utility.Config;
import utility.ErrorLayout;
import utility.ResizeImage;

import static android.R.attr.path;

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
    MultipartBody.Part body;

String imagepath="";


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


        String s=Config.IMAGE_PATH;
        encodeImage(s);

       ResizeImage resize= new ResizeImage(ActTestReport.this);


        //resize.compressImage(s);
        String comPath= resize.compressImage(s);
        Bitmap  photo = BitmapFactory.decodeFile(comPath);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 10, stream);
        byte[] byteArray = stream.toByteArray();
        imagepath = Base64Decode.encodeBytes(byteArray);


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

    @OnClick({R.id.bt_submit,R.id.bt_restart})//R.id.login_btn
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_restart:
                Intent in = new Intent(context,ActDiffApplicants.class);
                startActivity(in);
                finishAllActivities();
                break;
            case R.id.bt_submit:

                /*Intent in1 = new Intent(context, ActResult.class);
                startActivity(in1);*/

                callApi();



                break;





        }
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

                        Intent in1 = new Intent(context, ActResult.class);
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

        JsonObject jsonObject= Functions.getClient().getJsonMapObject("RTOCODE",Config.RTO_CODE,
                "DriverNo", String.valueOf(Config.DRIVER_NUMBER),
                "Receipt_No",Config.RECEIPT_NUMBER,
                "Ref_Number",Config.REF_NUMBER,
                "LL_NO",Config.LICENCE_NUMBER,
                "picture", imagepath);


baseRequest.callAPIPost(1,jsonObject,"Get_Applicant_list.svc/Get_SaveApplicantBio/Get_BioInfo");


    }

}

