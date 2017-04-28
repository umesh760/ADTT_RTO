package com.rto_driving_test.Authorization;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.rto_driving_test.R;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import utility.BaseActivity;

public class VehicalSelectionActivity extends BaseActivity {

    String type[]={"Please Select","Two Wheeler","Four Wheeler"};
    ArrayAdapter<String> ad;
   /* @BindView(R.id.spin_vehical)
    Spinner spinner;*/
    @BindView(R.id.btn_next)
    Button next_btn;
    @BindView(R.id.btn_next_four)
    Button next_btnfour;
    JSONObject jsonObject=null;

    String typetest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehical_selection);
        ButterKnife.bind(this);
        setAppBar(getAppString(R.string.vehical_class), true);


        Intent i=new Intent();
        typetest=getIntent().getStringExtra("type");
        if(typetest.equalsIgnoreCase("fresh"))
        {
            try {
                jsonObject = new JSONObject(getIntent().getStringExtra("dataobject"));
            }
            catch (Exception e)
            {
                System.out.println(""+e.toString());
            }

        }





       /* ad=new ArrayAdapter<String>(getApplicationContext(),R.layout.spin_item,type);
        spinner.setAdapter(ad);*/

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*startActivity(new Intent(getApplicationContext(),ActTestReport.class));
                finish();*/


                if(typetest.equalsIgnoreCase("fresh")) {
                    Intent intent = new Intent(getApplicationContext(), ActTestReport.class);
                    intent.putExtra("vehical", "twowheeler");
                    intent.putExtra("dataobject", jsonObject.toString());
                    intent.putExtra("type",typetest);
                    startActivity(intent);
                }
                else if(typetest.equalsIgnoreCase("retest")) {

                    Intent intent = new Intent(getApplicationContext(), ActTestReport.class);
                    /*intent.putExtra("vehical", "twowheeler");
                    intent.putExtra("dataobject", jsonObject.toString());*/
                    intent.putExtra("type",typetest);
                    intent.putExtra("vehical", "twowheeler");
                    intent.putExtra("dataobject", "");
                    startActivity(intent);

                }



            }
        });

        next_btnfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(typetest.equalsIgnoreCase("fresh")){

                    Intent intent=new Intent(getApplicationContext(),ActTestReport.class);
                    intent.putExtra("vehical","fourwheeler");
                    intent.putExtra("dataobject",jsonObject.toString());
                    intent.putExtra("type",typetest);
                    startActivity(intent);

                }
                else {


                    Intent intent=new Intent(getApplicationContext(),ActTestReport.class);
                    intent.putExtra("type",typetest);
                    intent.putExtra("vehical","fourwheeler");
                    intent.putExtra("dataobject", "");
                    startActivity(intent);

                }


            }
        });


    }
}
