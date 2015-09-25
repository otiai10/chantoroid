package com.otiai10.chantroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class RoomActivity extends Activity {

    private String currentServerURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        // WebView.setWebContentsDebuggingEnabled(true);

        WebView oppaiWV = (WebView)this.findViewById(R.id.oppaiWebView);
        oppaiWV.setWebViewClient(new RoomIntentRulesWebViewClient());

        // {{{
        oppaiWV.getSettings().setJavaScriptEnabled(true);
        oppaiWV.getSettings().setDomStorageEnabled(true);
        oppaiWV.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // }}}

        Intent intent = this.getIntent();
        String url = intent.getStringExtra("url");

        this.currentServerURL = url;

        oppaiWV.loadUrl(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_room, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class RoomIntentRulesWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String u) {
            Uri uri = Uri.parse(u);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        }
    }
}
