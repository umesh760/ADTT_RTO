package com.rto_driving_test.Authorization;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.rto_driving_test.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehical_selection);
        ButterKnife.bind(this);
        setAppBar(getAppString(R.string.vehical_class), true);
       /* ad=new ArrayAdapter<String>(getApplicationContext(),R.layout.spin_item,type);
        spinner.setAdapter(ad);*/

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ActTestReport.class));
                finish();
            }
        });

        next_btnfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),ActTestReport.class));
                finish();

            }
        });


    }
}
