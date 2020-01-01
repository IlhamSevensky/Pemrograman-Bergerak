package com.example.backgroundthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;

interface MyAsyncCallBack {
    void onPreExecute();
    void onPostExecute(String text);
}

public class MainActivity extends AppCompatActivity implements MyAsyncCallBack {

    TextView tvDesc, tvStatus;
    private final static String INPUT_STRING = "Halo Ini Demo AsyncTask!!";

    @Override
    public void onPreExecute() {
        tvStatus.setText(R.string.status_pre);
        tvDesc.setText(INPUT_STRING);
    }

    @Override
    public void onPostExecute(String text) {
        tvStatus.setText(R.string.status_post);

        if (text != null) {
            tvDesc.setText(text);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tv_status);
        tvDesc = findViewById(R.id.tv_desc);

        DemoAsync demoAsync = new DemoAsync(this);
        demoAsync.execute(INPUT_STRING);
    }

    private static class DemoAsync extends AsyncTask<String, Void, String> {
        static final String LOG_ASYNC = "DemoAsync";
        WeakReference<MyAsyncCallBack> myListener ;
        DemoAsync(MyAsyncCallBack myListener){
            this.myListener= new WeakReference<>(myListener);
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            Log.d(LOG_ASYNC, "status : onPreExecute");

            MyAsyncCallBack myListener = this.myListener.get();
            if (myListener!= null){
                myListener.onPreExecute();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(LOG_ASYNC, "status : doInBackround");
            String output = null;
            try {
                String input = strings[0];
                output = input + "Selamat Belajar!!!";
                Thread.sleep(2000);
            }catch (Exception e){
                Log.d(LOG_ASYNC, e.getMessage());
            }

            return output;
        }

        @Override
        public void onPostExecute(String text) {
            super.onPostExecute(text);
            Log.d(LOG_ASYNC, "status : onPostExecute");

            MyAsyncCallBack myListener = this.myListener.get();

            if (myListener != null){
                myListener.onPostExecute(text);
            }
        }
    }
}
