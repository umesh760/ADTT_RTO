package com.rto_driving_test.Authorization;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rto_driving_test.Adapters.DateSelectAdapter;
import com.rto_driving_test.Adapters.OnItemClickAdapter;
import com.rto_driving_test.Models.AppoimentBean;
import com.rto_driving_test.Models.AppointmentModel;
import com.rto_driving_test.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.BaseRequest;
import retrofit.RequestReciever;
import utility.BaseActivity;
import utility.Config;
import utility.ErrorLayout;

public class ActAppoimentList extends BaseActivity {

    Context context;
    RecyclerView recyViewAppoi;
    Activity activity;
    ArrayList<AppoimentBean> std10List = new ArrayList<AppoimentBean>();

    @BindView(R.id.et_search_applicant)
    EditText edTxtSearch;
    DateSelectAdapter dateSelectAdapter;

    ArrayList<AppointmentModel> appointmentModels=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.act_appoiment);
        context = this;
        activity = this;
        ButterKnife.bind(this);
        setAppBar(getAppString(R.string.mid_screen), true);
        initViews();
//        appointmentModels=new ArrayList<>();
        recyViewAppoi = (RecyclerView) findViewById(R.id.rc_view_select);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(activity);
        recyViewAppoi.setLayoutManager(mLayoutManager2);
//        recyViewAppoi.setItemAnimator(new DefaultItemAnimator());
//        recyViewAppoi.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));


        DummyData();
        callApi();

        edTxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = edTxtSearch.getText().toString().trim();
                dateSelectAdapter.filter(searchText);

            }
        });
    }




    BaseRequest baseRequest_list;
    private void callApi() {

        baseRequest_list=new BaseRequest(ActAppoimentList.this);
        baseRequest_list.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {

                JSONArray data = (JSONArray) object;
                appointmentModels = baseRequest_list.getDataList(data, AppointmentModel.class);
                dateSelectAdapter.setList(appointmentModels);



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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            baseRequest_list.callAPIGET(1,new ArrayMap<String, String>(),"Get_Applicant_list.svc/Get_Applicant/");
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

    @OnClick({})
    public void onClick(View view) {
        switch (view.getId()) {



        }
    }


    BaseRequest baseRequest;

//    private void callAPI() {
//        baseRequest = new BaseRequest(this);
//        baseRequest.setBaseRequestListner(new RequestReciever() {
//            @Override
//            public void onSuccess(int requestCode, String Json, Object object) {
//                try {
//                    JSONArray array = (JSONArray) object;
//                    std10List.clear();
//                    if (array != null && array.length() > 0) {
//                        for (int i = 0; i < array.length(); i++) {
//                            Gson gson = new Gson();
//                            JSONObject jo10 = null;
//                            try {
//                                jo10 = array.getJSONObject(i);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            AppoimentBean    stdData10 = gson.fromJson(jo10.toString(), AppoimentBean.class);
//                            std10List.add(stdData10);
//                        }
//
//
////                        Log.e("", "shyam Login object= " + object + " Json= " + Json);
//                    } else {
//                        Toast.makeText(mContext, "No Data", Toast.LENGTH_SHORT).show();
//                    }
//                    SetData();
//                } catch (JsonSyntaxException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(int requestCode, String errorCode, String message) {
//                errorLayout.showAlert(message, ErrorLayout.MsgType.Error);
//
//            }
//
//            @Override
//            public void onNetworkFailure(int requestCode, String message) {
//                errorLayout.showAlert(message, ErrorLayout.MsgType.Error);
//
//            }
//        });
//
//
//        JsonObject object = Functions.getClient().getJsonMapObject("centerno", sCenNo,
//                "password",sPass
//
//        );
//        //http://cnvg.in/cbseecm/api_datewiseList.php
//        baseRequest.callAPIPost(1, object, getAppString(R.string.api_list));
//    }

public void DummyData()
{
    for(int i=0;i<5;i++)
    {
        AppoimentBean bean=new AppoimentBean();
        bean.setAppId(i+"");
        bean.setApPic("");
        bean.setApName("Rekesh Kumar Verma");
        bean.setApAppNo("305353503503");
        std10List.add(bean);
    }
    SetData();
}
    public void SetData()
    {
         dateSelectAdapter = new DateSelectAdapter(ActAppoimentList.this, appointmentModels, new OnItemClickAdapter() {
             @Override
             public void click(int idofClickedItem, Object object, int position) {




                         //String id=appointmentModels.get(position).getReceipt_Number();

//                    mcontext.startActivity(new Intent(mcontext, LoginActivity.class).putExtra("app_id",id));

                         AppointmentModel appointmentModel=new AppointmentModel();
                         appointmentModels.add(appointmentModel);


                         String name=appointmentModels.get(position).getApplicant_Name();

                 Config.APPLICANT_FIRST_NAME=appointmentModels.get(position).getApplicant_Name();
                 Config.APPLICANT_LAST_NAME=appointmentModels.get(position).getApplicant_Last_Name();
                 Config.RTO_CODE=appointmentModels.get(position).getRTO_CODE();
                 Config.APPOINTMENT_DATE=appointmentModels.get(position).getAppointment_Date();
                 Config.DRIVER_NUMBER=appointmentModels.get(position).getDRIVER_NUMBER();
                 Config.NUMBER=appointmentModels.get(position).getNumber();
                 Config.LICENCE_NUMBER=appointmentModels.get(position).getLicence_Number();
                 Config.DOB=appointmentModels.get(position).getDate_Of_Birth();
                 Config.RECEIPT_NUMBER=appointmentModels.get(position).getReceipt_Number();
                 Config.REF_NUMBER=appointmentModels.get(position).getReference_Number();
                 Config.SOWODO=appointmentModels.get(position).getSo_Wo_Do();





                        // Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_LONG).show();

                         Intent in = new Intent(getApplicationContext(),ActRetestAppliDetails.class);
                         in.putExtra("diff","fresh");
                         in.putExtra("fresh","fresh");
                         in.putExtra("name",name);
                         startActivity(in);


             }
         });


                 recyViewAppoi.setAdapter(dateSelectAdapter);
    }




    Dialog logoutDlg = null;

    public void logout(String mes, final int dif) {
        try {
            if (logoutDlg != null) {
                logoutDlg.cancel();
            }
            logoutDlg = new Dialog(ActAppoimentList.this, R.style.MyDialogTheme1);
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
