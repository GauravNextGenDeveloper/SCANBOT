package com.example.scanbot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.scanbot.R;

public class WebviewActivity extends AppCompatActivity {

    WebView webview;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Loading....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        webview = findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webview.setWebChromeClient(new WebChromeClient());

        webview.loadUrl("https://sandbox.paykun.com/payment/87380-40193-16743-46255");


        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                // TODO show you progress image
                super.onPageStarted(view, url, favicon);
                if (!progressDialog.isShowing())
                {
                    progressDialog.show();
                }

            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                // TODO hide your progress image
                super.onPageFinished(view, url);
                if (progressDialog.isShowing()) {
                    progressDialog.hide();
                }
            }
        });

    }
}
