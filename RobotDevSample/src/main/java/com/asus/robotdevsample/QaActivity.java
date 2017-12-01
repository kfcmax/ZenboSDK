package com.asus.robotdevsample;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.robotframework.API.RobotAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QaActivity extends AppCompatActivity{

    private TextView txvResult,answer,title,Q,ans;
    private Context context;
    private Button result;
    private SpeechRecognizer speech=null;
    private TextToSpeech tts;
    private ImageView start;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa);
        context = getApplicationContext();
        txvResult = (TextView) findViewById(R.id.txvResult);
        answer= (TextView)findViewById(R.id.answer);
        title= (TextView)findViewById(R.id.textView11);
        Q= (TextView)findViewById(R.id.textView10);
        ans= (TextView)findViewById(R.id.textView9);
        start = (ImageView)findViewById(R.id.btnSpeak);

        //result = (Button)findViewById(R.id.button);
        RobotAPI API= new RobotAPI(context);
        Bundle qa =this.getIntent().getExtras();


        int topic = qa.getInt("topic");

        if (topic == 1){
            title.setText("商品諮詢");
            API.robot.speak("歡迎來到一安藥局，有什麼問題想問我嗎?");
            API.robot.speak("按螢幕下方的麥克風就可以開始問問題喔");
            answer.setText("歡迎來到一安藥局，有什麼問題想問我嗎?\n"+"按螢幕下方的麥克風就可以開始問問題喔");
        }
        if (topic == 2){
            title.setText("商品位置");
            API.robot.speak("歡迎來到一安藥局，有什麼問題想問我嗎?");
            API.robot.speak("按螢幕下方的麥克風就可以開始問問題喔");
            answer.setText("歡迎來到一安藥局，有什麼問題想問我嗎?\n"+"按螢幕下方的麥克風就可以開始問問題喔");

        }
        if (topic == 3){
            title.setText("藥局資訊");
            API.robot.speak("歡迎來到一安藥局，以下是本店資訊");
            answer.setText("營業時間 :  一到日 8:30 - 23:00 \n" +
                            "地址: 新北市新莊區新泰路268號 \n"+
                            "連絡電話: 02-2994-1618 \n"+
                            "門市分布:\n"+
                            "1. 泰山區 泰山店  2. 新莊區 新泰店  3. 土城區 延吉店  4. 中和區 中和店 \n"+
                            "5. 板橋區 南雅店  6. 新莊區 新杏店  7. 松山區 正昇店  8. 蘆洲區 乙安店");
            start.setEnabled(false);
            answer.setTextSize(33);
        }
        if (topic == 5){
            title.setText("流感問答");
            API.robot.speak("天氣早晚變化大，最近很多人都得到流感，要注意身體健康喔");
            answer.setText("天氣早晚變化大，最近很多人都得到流感，要注意身體健康喔\n"+"按螢幕下方的麥克風就可以開始問問題喔");
        }



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
                if (resultCode == RESULT_OK) {                                                        //    if (resultCode == RESULT_OK && data != null) {
                    final ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txvResult.setText(result.get(0)+"?");
                    //API.robot.speak(result.get(0));

                    new AsyncTask<Void,Void,String>(){
                        @Override
                        protected String doInBackground(Void... params) {
                            RobotAPI API= new RobotAPI(context);


                            String sss = "";
                            String a = "安安";

                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url("http://140.116.156.225:5000/"+ result.get(0)).build();

                            try {
                                final Response response = client.newCall(request).execute();
                                sss = response.body().string();
                                API.robot.speak(sss);
//                                API.robot.speak("那你知道藥局的營業時間嗎?");
//                                tts.speak("那請問您想吃甚麼類型的中餐呢", TextToSpeech.QUEUE_ADD, null);
//                                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//



                            } catch (IOException e) {
                                e.printStackTrace();
                            }

//                            if (sss == a){
//                                API.robot.speak("我聽不清楚，請您再說一次");
//                            }
//                            else{
//                                 API.robot.speak("");
//                            }


                            return sss;

                        }
                        @Override
                        protected void onPostExecute(final String sss) {
                            super.onPostExecute(sss);
                            RobotAPI API= new RobotAPI(context);

                            Q.setText("問:");
                            ans.setText("答:");
                            answer.setText(sss);

                        }

                    }.execute();




                }

                break;

        }
    }

///551HIHIHIHIHIHIHIHIHIHIHIHIHIHIHI
//5656+++++++++++++++++++++++++++++++++++++++++++

}
