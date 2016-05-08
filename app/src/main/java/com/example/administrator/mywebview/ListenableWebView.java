package com.example.administrator.mywebview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/*******************************************************************************
 * Author  : Yankai
 * Date    : 2016-04-07 11:47
 * Email   : yk_yang@wesugarfree.com
 * Company : 上海无糖运动
 ******************************************************************************/

public class ListenableWebView extends WebView {

    private IWebViewScroll mIWebViewScroll;

    public ListenableWebView(Context context) {
        this(context, null);
    }

    public ListenableWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListenableWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mIWebViewScroll != null && t == 0) {
            mIWebViewScroll.onTop();
        } else if (mIWebViewScroll != null && t != 0) {
            mIWebViewScroll.notOnTop();
        }
    }


    /**
     * 设置滑动监听
     *
     * @param listener
     */
    public void setOnCustomScrollChanged(IWebViewScroll listener) {
        this.mIWebViewScroll = listener;
    }
}