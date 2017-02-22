package io.happylrd.smartassistant.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.ProgressBar;

import io.happylrd.smartassistant.R;
import io.happylrd.smartassistant.utils.LogUtil;

public class NewsDetailActivity extends BaseActivity {

    private static final String PREFIX = "io.happylrd.smartassistant.ui.";
    private static final String NEWS_TITLE = PREFIX + "title";
    private static final String NEWS_URL = PREFIX + "url";

    private ProgressBar mProgressBar;
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        initView();
    }

    public static Intent newIntent(Context packageContext, String title, String url) {
        Intent intent = new Intent(packageContext, NewsDetailActivity.class);
        intent.putExtra(NEWS_TITLE, title);
        intent.putExtra(NEWS_URL, url);
        return intent;
    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mWebView = (WebView) findViewById(R.id.webView);

        Intent intent = getIntent();
        String title = intent.getStringExtra(NEWS_TITLE);
        final String url = intent.getStringExtra(NEWS_URL);
        LogUtil.i("url:" + url);

        getSupportActionBar().setTitle(title);

        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);

        mWebView.setWebChromeClient(new WebViewClient());

        mWebView.loadUrl(url);

        mWebView.setWebViewClient(new android.webkit.WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public class WebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
