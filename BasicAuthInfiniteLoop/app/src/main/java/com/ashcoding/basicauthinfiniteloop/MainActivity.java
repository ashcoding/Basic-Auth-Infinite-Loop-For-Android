package com.ashcoding.basicauthinfiniteloop;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "InfiniteLoop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);

        Boolean badCredentials = true;

        String username = badCredentials? "JohnConner" : "Sarah";
        String password = "testpass";

        Authenticator.setDefault(new CustomAuthenticator(null, username, password));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Button Pressed");
                new UrlTask().execute();
            }
        });
    }

    class UrlTask extends AsyncTask<String, Void, Void> {

        private Exception exception;

        protected Void doInBackground(String... urls) {
            try {
                Log.d(TAG, "Section 1");
                HttpURLConnection conn = null;

                // Please change this URL to the IP Address of the node server
                URL url = new URL("http://192.168.0.113:1337/");

                Log.d(TAG, "Section 2:"+url.getPort()+" web"+url.getPath());
                conn = (HttpURLConnection) url.openConnection();

                Log.d(TAG, "Section 3");
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setUseCaches(false);
                // Starts the query

                Log.d(TAG, "Section 4");
                int response = conn.getResponseCode();
                Log.d(TAG, "The response is: " + response);

            }catch (Exception e) {
                Log.d(TAG, "Error:"+e.toString());
            }

            return null;
        }

    }
}
