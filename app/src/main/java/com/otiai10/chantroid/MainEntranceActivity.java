package com.otiai10.chantroid;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainEntranceActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_entrance);

        // delegate button events
        Button entryButton = (Button)this.findViewById(R.id.EntryButton);
        entryButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_entrance, menu);
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

    public void onClick(View v) {
        EditText serverURL = (EditText)this.findViewById(R.id.serverURL);
        String url = serverURL.getText().toString();
        if (url.isEmpty() || url.matches("")) {
            return; // abort
        }

        Log.d(this.getLocalClassName(), url + ":" + this.checkInputIsValidURL(url));
        if (this.checkInputIsValidURL(url)) {
            Log.d(this.getLocalClassName(), "ここでほぞん");
            /*
            Intent intent = new Intent(this, RoomActivity.class);
            intent.putExtra("url", url);
            this.startActivity(intent);
            */
            return;
        }
    }

    private boolean checkInputIsValidURL(String input) {
        Pattern URL_PATTERN = Patterns.WEB_URL;
        return URL_PATTERN.matcher(input).matches();
    }
}
