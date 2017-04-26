package com.rto_driving_test.Authorization;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rto_driving_test.R;

import utility.BaseActivity;

public class PendingTest extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_test);

        setAppBar("Pending Test",true);
    }
}
