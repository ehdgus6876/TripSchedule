package com.example.tripschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

public class WebView extends AppCompatActivity {

    private android.webkit.WebView mWebView;
    private WebSettings mWebSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        String url=getIntent().getStringExtra("url");

        mWebView=findViewById(R.id.webView);

        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings=mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportMultipleWindows(false);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setDomStorageEnabled(true);

        mWebView.loadUrl(url);


    }
}
