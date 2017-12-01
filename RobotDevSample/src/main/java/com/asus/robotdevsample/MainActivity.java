package com.asus.robotdevsample;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.asus.robotframework.API.MotionControl;
import com.asus.robotframework.API.RobotAPI;
import com.asus.robotframework.API.RobotCallback;
import com.asus.robotframework.API.RobotCmdState;
import com.asus.robotframework.API.RobotErrorCode;
import com.asus.robotframework.API.RobotFace;
import com.asus.robotframework.API.results.DetectPersonResult;
import com.asus.robotframework.API.results.GesturePointResult;
import com.asus.robotframework.API.results.RecognizePersonResult;
import com.robot.asus.robotactivity.RobotActivity;

import org.json.JSONObject;

import java.util.List;

public class MainActivity extends RobotActivity{

    Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9,bt10;

    private static Context context;
    private static Context mContext;
    private ViewPager mViewPager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        robotAPI.vision.cancelDetectPerson();
        try{
            // delay 1 second
            Thread.sleep(2000);

        } catch(InterruptedException e){
            e.printStackTrace();

        }

        robotAPI.motion.stopMoving();
        robotAPI.robot.stopSpeak();


        mContext = this;
        //title
        context = getApplicationContext();




        bt1 = (Button)findViewById(R.id.button1);
        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(myIntent);
            }
        });

        bt2 = (Button)findViewById(R.id.button2);
        bt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

//                Intent myIntent = new Intent(MainActivity.this, VisionRequestDetectPerson.class);
//                startActivity(myIntent);
                robotAPI.robot.speak("你好我是一安藥局小幫手，有什麼事情可以找我喔");
                detectPersonClicked();
            }
        });

        bt3 = (Button)findViewById(R.id.button3);
        bt3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(MainActivity.this, sale.class);
                startActivity(myIntent);
            }
        });

        bt4 = (Button)findViewById(R.id.button4);                                   //商品諮詢
        bt4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(MainActivity.this, QaActivity.class);


                //new一個Bundle物件，並將要傳遞的資料傳入
                Bundle bundle = new Bundle();
                bundle.putInt("topic",1 );


                //將Bundle物件assign給intent
                myIntent.putExtras(bundle);


                startActivity(myIntent);
            }
        });


        bt5 = (Button)findViewById(R.id.button5);                               //商品位置
        bt5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(MainActivity.this, QaActivity.class);
                //new一個Bundle物件，並將要傳遞的資料傳入
                Bundle bundle = new Bundle();
                bundle.putInt("topic",2 );

                //將Bundle物件assign給intent
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });

        bt6 = (Button)findViewById(R.id.button6);                              //藥局資訊
        bt6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(MainActivity.this, QaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("topic",3 );

                //將Bundle物件assign給intent
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });

        bt7 = (Button)findViewById(R.id.button7);                             //健康知識問答
        bt7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(MainActivity.this, Private.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("topic",4 );
//
//                //將Bundle物件assign給intent
//                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });

        bt8 = (Button)findViewById(R.id.button8);                         //流感問答
        bt8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(MainActivity.this, QaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("topic",5 );

                //將Bundle物件assign給intent
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });
        bt9 = (Button)findViewById(R.id.button9);                           //私密商品
        bt9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(MainActivity.this, Private.class);

                startActivity(myIntent);
            }
        });

        bt10 = (Button)findViewById(R.id.button100);                           //私密商品
        bt10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                PackageManager packageManager = getPackageManager();
                Intent intent = packageManager.getLaunchIntentForPackage("com.ian.prescriptions");
                startActivity(intent);

            }
        });

    }


    private void detectPersonClicked() {
        robotAPI.vision.requestDetectPerson(3000);
        robotAPI.robot.setExpression(RobotFace.DEFAULT);
    }




    @Override
    protected void onPause() {
        super.onPause();


    }


    @Override
    protected void onResume() {
        super.onResume();



    }


    public static RobotCallback robotCallback = new RobotCallback() {


        @Override
        public void onResult(int cmd, int serial, RobotErrorCode err_code, Bundle result) {
            super.onResult(cmd, serial, err_code, result);
        }

        @Override
        public void onStateChange(int cmd, int serial, RobotErrorCode err_code, RobotCmdState state) {
            super.onStateChange(cmd, serial, err_code, state);
        }
        @Override
        public void onEnrollUpdate(float progress, int error, String uuid) {
            Log.d("RobotDevSample", "onEnrollUpdate progress is" + progress + ". error is " + error + ". id is " + uuid);
        }

        @Override
        public void onEnrollGetAllIDList(List<String> faceIDList) {
            super.onEnrollGetAllIDList(faceIDList);
            Log.d("RobotDevSample", "onEnrollGetAllIDList: " + faceIDList.toString());
        }

        @Override
        public void onRecognizePersonResult(List<RecognizePersonResult> resultList) {
            super.onRecognizePersonResult(resultList);
            if (resultList.size() == 0)
                Log.d("RobotDevSample", "onRecognizePersonResult: empty");
            else
                Log.d("RobotDevSample", "onRecognizePersonResult: " + resultList.get(0).getUuid());
        }

        @Override
        public void onDetectPersonResult(List<DetectPersonResult> resultList) {
            super.onDetectPersonResult(resultList);
            RobotAPI API= new RobotAPI(context);
            Intent intent = new Intent(mContext, MainActivity.class);


            if (resultList.size() == 0 || resultList.get(0).getTrackID() == -50) {
                Log.d("RobotDevSample", "onDetectPersonResult: empty");

                API.vision.cancelDetectPerson();
                API.motion.stopMoving();
                API.robot.stopSpeak();
            }
            else {
                Log.d("RobotDevSample", "onDetectPersonResult: " + resultList.get(0).getBodyLoc().toString());
                Log.d("RobotDevSample", "resultList.size(): " + resultList.size());

                API.vision.cancelDetectPerson();
                API.robot.setExpression(RobotFace.HAPPY);

               // API.motion.moveBody(0,0,180, MotionControl.SpeedLevel.Body.L4);
                API.robot.speak("歡迎光臨");
                API.motion.moveBody(0,0,360, MotionControl.SpeedLevel.Body.L3);


                API.robot.setExpression(RobotFace.HIDEFACE);

                mContext.startActivity(intent);




                //use toast to show detect person
                String toast_result = String.valueOf(resultList.get(0));
                Toast toast = Toast.makeText(context, toast_result, Toast.LENGTH_SHORT);
                toast.show();
                resultList.clear();
                API.vision.cancelDetectPerson();

            }

        }


        @Override
        public void onGesturePoint(GesturePointResult result) {
            Log.d("RobotDevSample", "onGesturePoint:" +result.toString());
        }
    };


    public static RobotCallback.Listen robotListenCallback = new RobotCallback.Listen() {
        @Override
        public void onFinishRegister() {

        }

        @Override
        public void onVoiceDetect(JSONObject jsonObject) {

        }

        @Override
        public void onSpeakComplete(String s, String s1) {

        }

        @Override
        public void onEventUserUtterance(JSONObject jsonObject) {

        }

        @Override
        public void onResult(JSONObject jsonObject) {

        }

        @Override
        public void onRetry(JSONObject jsonObject) {

        }
    };



    public MainActivity() {
        super(robotCallback, robotListenCallback);
    }

}


