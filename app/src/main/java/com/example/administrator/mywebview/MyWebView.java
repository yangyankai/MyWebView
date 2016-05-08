package com.example.administrator.mywebview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/*******************************************************************************
 * Author  : Yankai
 * Date    : 2016-04-20 18:02
 * Email   : yk_yang@wesugarfree.com
 * Company : 上海无糖运动
 ******************************************************************************/
public class MyWebView extends WebView {


    public MyWebView(Context context, AttributeSet attrs) {// 在基类中统一设置webview的设置
        super(context, attrs);
        getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
    }
}
