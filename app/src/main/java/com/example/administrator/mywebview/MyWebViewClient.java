package com.example.administrator.mywebview;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/*******************************************************************************
 * Author  : Yankai
 * Date    : 2016-04-20 19:16
 * Email   : yk_yang@wesugarfree.com
 * Company : 上海无糖运动
 ******************************************************************************/
public class MyWebViewClient extends WebViewClient {

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        String data = "页面加载错误！";
        view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.cancel();
    }
}
