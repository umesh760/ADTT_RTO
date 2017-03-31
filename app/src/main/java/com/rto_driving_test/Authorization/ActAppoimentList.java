package com.rto_driving_test.Authorization;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.rto_driving_test.Models.AppoimentBean;
import com.rto_driving_test.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.BaseRequest;
import utility.BaseActivity;
import utility.ErrorLayout;

public class ActAppoimentList extends BaseActivity {

    Context context;
    RecyclerView recyViewAppoi;
    Activity activity;
    ArrayList<AppoimentBean> std10List = new ArrayList<AppoimentBean>();

    @BindView(R.id.et_search_applicant)
    EditText edTxtSearch;

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
        recyViewAppoi = (RecyclerView) findViewById(R.id.rc_view_select);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(activity);
        recyViewAppoi.setLayoutManager(mLayoutManager2);
//        recyViewAppoi.setItemAnimator(new DefaultItemAnimator());
//        recyViewAppoi.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));


        DummyData();

        edTxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                String searchText = edTxtSearch.getText().toString().trim();
//                adpt.filter(searchText);
            }
        });
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
        DateSelectAdapter  studentDetailsRow = new DateSelectAdapter(std10List, activity);
        recyViewAppoi.setAdapter(studentDetailsRow);
    }


    public class DateSelectAdapter extends RecyclerView.Adapter<DateSelectAdapter.MyViewHolder> {
        private List<AppoimentBean> stdData10s;
        private Activity mcontext;
        int index;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tvName,tvId;
            ImageView status;
            RelativeLayout rlMain;

            public MyViewHolder(View view) {
                super(view);
                tvName = (TextView) view.findViewById(R.id.tv_date);
                tvId = (TextView) view.findViewById(R.id.tv_id);
                status = (ImageView) view.findViewById(R.id.iv_date);
                rlMain = (RelativeLayout) view.findViewById(R.id.rl_select_date);

            }
        }

        public DateSelectAdapter(List<AppoimentBean> stdData10s, Activity context) {
            this.stdData10s = stdData10s;
            this.mcontext = context;

        }

        @Override
        public DateSelectAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custome_appoiment, parent, false);
            return new DateSelectAdapter.MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(final DateSelectAdapter.MyViewHolder holder, final int position) {
            index = position;
//            AppoimentBean stdData10 = stdData10s.get(position);
             
        holder.tvName.setText(stdData10s.get(position).getApName());
        holder.tvId.setText("Id: "+stdData10s.get(position).getApAppNo());
         if(stdData10s.get(position).getApPic().equals("")||stdData10s.get(position).getApPic()=="")
         {
//             tdData10s.get(position)
             holder.status.setBackgroundResource(R.drawable.pic);
         }
            else
         {
             Picasso.with(mcontext).load(stdData10s.get(position).getApPic()).into(holder.status);
         }


            holder.rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id=stdData10s.get(position).getApAppNo();

//                    mcontext.startActivity(new Intent(mcontext, LoginActivity.class).putExtra("app_id",id));

                    Intent in = new Intent(context,ActRetestAppliDetails.class);
                    in.putExtra("diff","fresh");
                    mcontext.startActivity(in);
                }
            });
        }
        @Override
        public int getItemCount() {
            return stdData10s.size();
        }
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
