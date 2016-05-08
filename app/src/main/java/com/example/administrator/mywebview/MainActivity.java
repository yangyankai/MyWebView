package com.example.administrator.mywebview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView) findViewById(R.id.web_view);

        webView.setWebViewClient(new MyWebViewClient() {

        });

        webView.loadUrl("http://114.55.1.119:8080/member/package/index");
    }
}
