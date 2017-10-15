package com.asus.robotdevsample;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.robotframework.API.RobotAPI;

import java.util.ArrayList;
import java.util.Locale;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QaActivity extends AppCompatActivity {

    private TextView txvResult;
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa);
        context = getApplicationContext();
        txvResult = (TextView) findViewById(R.id.txvResult);

    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RobotAPI API= new RobotAPI(context);
        HttpAsyncTask task = new HttpAsyncTask();
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    final ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txvResult.setText(result.get(0));
                    //API.robot.speak(result.get(0));
                    new AsyncTask<Void,Void,String>(){
                        @Override
                        protected String doInBackground(Void... params) {
                            RobotAPI API= new RobotAPI(context);

                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url("http://220.134.39.80:5000/"+ result.get(0)).build();

                            try {
                                Response response = client.newCall(request).execute();
                                API.robot.speak(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            return null;
                        }
                    }.execute();

                }
                break;
        }
    }




}
