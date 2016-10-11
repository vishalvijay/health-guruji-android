package com.v4creation.health_guruji;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HGWebViewClient extends WebViewClient {

    private final WebView webView;
    private HGWebViewCallBacks callback;
    private boolean loadingFinished = true;
    private boolean redirect = false;


    public HGWebViewClient(WebView webView, HGWebViewCallBacks callBacks) {
        this.callback = callBacks;
        this.webView = webView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return handleShouldOverrideUrlLoadingBasedOnApi(url);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return handleShouldOverrideUrlLoadingBasedOnApi(request.getUrl().toString());
    }

    private boolean handleShouldOverrideUrlLoadingBasedOnApi(String url) {
        if (!loadingFinished) {
            redirect = true;
        }

        loadingFinished = false;
        webView.loadUrl(url);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        loadingFinished = false;
        callback.onWebViewStartLoading();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (!redirect) {
            loadingFinished = true;
        }

        if (loadingFinished && !redirect) {
            callback.onWebViewStopLoading();
        } else {
            redirect = false;
        }
    }

    public static interface HGWebViewCallBacks {
        public void onWebViewStartLoading();

        public void onWebViewStopLoading();
    }

}
