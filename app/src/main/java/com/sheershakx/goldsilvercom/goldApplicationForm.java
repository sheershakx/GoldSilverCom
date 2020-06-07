package com.sheershakx.goldsilvercom;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class goldApplicationForm extends AppCompatActivity {

    WebView webView;

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_application_form);

        webView = findViewById(R.id.webview);     //typecasting
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(false);
        webView.loadUrl("https://www.google.com");



        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Toast.makeText(goldApplicationForm.this, "Page Loading...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Toast.makeText(goldApplicationForm.this, "Page Loaded", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
