package com.otiai10.chantroid;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class MainEntranceActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private String SavedServerPrefKey = "SAVED_SERVERS";
    private String[] savedServerURLs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_entrance);

        // delegate button events
        Button entryButton = (Button)this.findViewById(R.id.EntryButton);
        entryButton.setOnClickListener(this);

        // 保存されてるアドレスを取得
        ListView savedServerURLsListView = (ListView)this.findViewById(R.id.savedServerURLsList);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> saved = sp.getStringSet(SavedServerPrefKey, new HashSet<String>());

        this.savedServerURLs = saved.toArray(new String[saved.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, this.savedServerURLs);

        savedServerURLsListView.setAdapter(adapter);
        savedServerURLsListView.setOnItemClickListener(this);


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

        final GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
        // これは単なるプロジェクトID
        // http://stackoverflow.com/questions/11294602/android-gcm-sender-id
        final String SENDER_ID = "721713162121";

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    String regid = gcm.register(SENDER_ID);
                    return "とれたやつ:" + regid;
                } catch (java.io.IOException err) {
                    return "しっぱい:" + err.toString();
                }
            }
            @Override
            protected void onPostExecute(String msg) {
                Log.d("onPostExecute", msg);
            }
        }.execute(null, null, null);

        /*
        EditText serverURL = (EditText)this.findViewById(R.id.serverURL);
        String url = serverURL.getText().toString();

        if (url.isEmpty() || url.matches("")) {
            return; // abort
        }

        if (!this.checkInputIsValidURL(url)) {
            return; // abort
        }

        intentToServerURL(url);
        */
    }

    /**
     * めんどくさいのでActivityにOnItemClickListener実装しちゃいます
     * @param adapter
     * @param v
     * @param position
     * @param a
     */
    public void onItemClick(AdapterView<?>adapter, View v, int position, long a) {
        intentToServerURL(savedServerURLs[position]);
    }

    private boolean checkInputIsValidURL(String input) {
        Pattern URL_PATTERN = Patterns.WEB_URL;
        return URL_PATTERN.matcher(input).matches();
    }

    private void intentToServerURL(String url) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> saved = sp.getStringSet(SavedServerPrefKey, new HashSet<String>());

        saved.add(url);
        sp.edit().putStringSet(SavedServerPrefKey, saved).apply();

        Intent intent = new Intent(this, RoomActivity.class);
        intent.putExtra("url", url);

        this.startActivity(intent);
    }
}
