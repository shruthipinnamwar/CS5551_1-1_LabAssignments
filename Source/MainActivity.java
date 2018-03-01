package com.filestack.android.demo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.filestack.Config;
import com.filestack.android.FsActivity;
import com.filestack.android.FsConstants;
import com.filestack.android.Selection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_FILESTACK = RESULT_FIRST_USER;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            IntentFilter intentFilter = new IntentFilter(FsConstants.BROADCAST_UPLOAD);
            UploadStatusReceiver receiver = new UploadStatusReceiver();
            LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Locale locale = Locale.getDefault();

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FILESTACK && resultCode == RESULT_OK) {
            Log.i(TAG, "received filestack selections");
            String key = FsConstants.EXTRA_SELECTION_LIST;
            ArrayList<Selection> selections = data.getParcelableArrayListExtra(key);
            for (int i = 0; i < selections.size(); i++) {
                Selection selection = selections.get(i);
                String msg = String.format(locale, "selection %d: %s", i, selection.getName());
                Log.i(TAG, msg);
            }
        }
    }

    public void openFilestack(View view) {
        Intent intent = new Intent(this, FsActivity.class);
        Config config = new Config("AgD7zz7xZTGyrQNojCbzqz");
        intent.putExtra(FsConstants.EXTRA_CONFIG, config);
        intent.putExtra(FsConstants.EXTRA_AUTO_UPLOAD, true);
        startActivityForResult(intent, REQUEST_FILESTACK);
    }

    public void openFileBrowser(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 0);
    }
}
