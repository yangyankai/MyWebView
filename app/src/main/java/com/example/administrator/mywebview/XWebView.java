package com.example.administrator.mywebview;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.logging.Logger;

/*******************************************************************************
 * Author  : Yankai
 * Date    : 2016-04-07 11:49
 * Email   : yk_yang@wesugarfree.com
 * Company : 上海无糖运动
 ******************************************************************************/
public class XWebView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {

    private ListenableWebView mWebView;//webview组件
    private SwipeRefreshLayout mSwipeRefreshLayout;//下拉刷新布局
    private String mUrl;//用户加载的链接
    private String mHtmlSource;//用户加载的html源码


    public XWebView(Context context) {
        this(context, null);
    }

    public XWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    //******************************************

    /**
     * 加载html代码
     *
     * @param htmlData
     */
    public void loadHtmlSource(String htmlData) {
        this.mHtmlSource = htmlData;
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
        mWebView.loadData(htmlData, "text/html; charset=UTF-8", null);
    }

    /**
     * 加载某个链接
     *
     * @param url
     */
    public void loadUrl(String url) {
        this.mUrl = url;
        mWebView.loadUrl(url);
    }

    /**
     * 释放webview
     */
    public void release() {
        mWebView.clearCache(true);
        mWebView.clearHistory();
        this.mWebView.destroy();
    }

    //******************************************
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.x_webview, this);
        mWebView = (ListenableWebView) this.findViewById(R.id.id_webview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.id_swipe_refresh_layout);
        initSwipeRefreshLayout();
        initWebView();

    }


    /**
     * webview的设置
     */
    private void initWebView() {
        //对webview是否处于顶部进行监听，解决webview往下拉后无法往上拉的冲突（和SwipeRefreshLayout冲突）
        mWebView.setOnCustomScrollChanged(new IWebViewScroll() {
            @Override
            public void onTop() {
                mSwipeRefreshLayout.setEnabled(true);
            }

            @Override
            public void notOnTop() {
                mSwipeRefreshLayout.setEnabled(false);
            }
        });
        mWebView.setVerticalScrollBarEnabled(false);//设置无垂直方向的scrollbar
        mWebView.setHorizontalScrollBarEnabled(false);//设置无水平方向的scrollbar
        WebSettings settings = mWebView.getSettings();
        settings.setSupportZoom(false); // 支持缩放
        settings.setBuiltInZoomControls(false); // 启用内置缩放装置
        settings.setJavaScriptEnabled(true); // 启用JS脚本
        // 设置WebView可触摸放大缩小
        settings.setBuiltInZoomControls(false);
        mWebView.setWebViewClient(new WebViewClient() {
            // 当点击链接时,希望覆盖而不是打开浏览器窗口
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description,
                                        String failingUrl) {
//                Log.e("onReceivedError");

                //用javascript隐藏系统定义的404页面信息
                mWebView.loadUrl("file:///android_asset/error.html");
                mSwipeRefreshLayout.setRefreshing(false);
                // mContainerFailView.setVisibility(View.VISIBLE);
                // mWebView.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mSwipeRefreshLayout.setRefreshing(true);
//                Log.e("onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mSwipeRefreshLayout.setRefreshing(false);
//                Log.e("onPageFinished");
            }
        });
        // 点击后退按钮,让WebView后退
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * 设置下拉刷新样式及监听
     */
    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }


    /**
     * 下拉刷新的监听
     */
    @Override
    public void onRefresh() {
//        if (NetworkUtil.isNetworkConnected(getContext())) {//有网络才允许重新刷新
            mWebView.reload();
//        } else {
//            mSwipeRefreshLayout.setRefreshing(false);//无网络
//            Toast.showShort(getContext(), TipMsg.HTTP_NO_CONNECTION);
//        }
    }


}
