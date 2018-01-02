package com.example.zac.project1;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;


public class image_1 extends AppCompatActivity {


    Button return_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_1);

        Button return_main = (Button) findViewById(R.id.return_main)  ;

        WebView  mWebView= (WebView) findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        mWebView.setWebChromeClient(new WebChromeClient());

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.loadUrl("file:///android_asset/index.html");



        return_main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent();
                intent1.setClass(image_1.this, MainActivity.class);
                startActivityForResult(intent1, 0);
                Toast.makeText(image_1.this, "返回主畫面", Toast.LENGTH_SHORT ).show();
            }
        });



    }
}
