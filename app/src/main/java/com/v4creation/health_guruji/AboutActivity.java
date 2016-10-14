package com.v4creation.health_guruji;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.v4creation.health_guruji.utils.Utils;

import io.fabric.sdk.android.Fabric;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(this);
        initViews();
    }

    private void initViews() {
        TextView versionTextView = (TextView) findViewById(R.id.tvVersion);
        versionTextView.setText(getResources().getString(R.string.version, Utils.getAppVersionName(getApplicationContext())));
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
