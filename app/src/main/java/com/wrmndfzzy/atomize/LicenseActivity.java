package com.wrmndfzzy.atomize;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class LicenseActivity extends AppCompatActivity {

    private WebView licView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        licView = (WebView) findViewById(R.id.licViews);
        licView.getSettings().setUseWideViewPort(true);
        licView.loadUrl("file:///android_asset/licenses.html");
    }
}
