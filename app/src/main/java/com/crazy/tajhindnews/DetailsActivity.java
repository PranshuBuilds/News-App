package com.crazy.tajhindnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class DetailsActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar loader;
    String url = "",Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        Title = intent.getStringExtra("Title");
        loader = findViewById(R.id.loader);
        webView = findViewById(R.id.webView);


        setSupportActionBar((Toolbar) findViewById(R.id.tool));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                loader.setVisibility(View.VISIBLE);
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                loader.setVisibility(View.GONE);
            }
        });

        webView.loadUrl(url);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_share:
                Intent intn =new Intent(Intent.ACTION_SEND);
                intn.setType("text/plain");

                String shareSub=Title+"\n\nTAJ HIND NEWS\nhttps://play.google.com/store/apps/details?id=com.crazy.tajhindnews";
                intn.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                intn.putExtra(Intent.EXTRA_TEXT,shareSub);
                startActivity(Intent.createChooser(intn,"SHARE with"));
                break;
        default:
            return super.onOptionsItemSelected(item);
    }
        return true;
    }
}
