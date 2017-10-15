package com.asus.robotdevsample;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.robotframework.API.MotionControl;
import com.asus.robotframework.API.RobotAPI;
import com.asus.robotframework.API.RobotCallback;
import com.asus.robotframework.API.RobotCmdState;
import com.asus.robotframework.API.RobotErrorCode;
import com.asus.robotframework.API.results.DetectPersonResult;
import com.asus.robotframework.API.results.GesturePointResult;
import com.asus.robotframework.API.results.RecognizePersonResult;
import com.robot.asus.robotactivity.RobotActivity;

import org.json.JSONObject;

import java.util.List;

public class VisionRequestDetectPerson extends RobotActivity {
    private Button mBtnDetectPerson;
    private Button mBtnCancelDetectPerson;

    private TextView mTextViewDetectPerson;
    private TextView mTextViewCancelDetectPerson;

    private static Context context;
    private static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_vision_request_detect_person_and_cancel);
        mContext = this;
        //title
        TextView mTextViewTitle = (TextView)findViewById(R.id.textview_title);
        mTextViewTitle.setText(getString(R.string.toolbar_title_subclass_vision_title));

        context = getApplicationContext();

        mTextViewDetectPerson = (TextView) findViewById(R.id.textview_vision_requestDetectPerson);
        mBtnDetectPerson = (Button) findViewById(R.id.DetectPerson);
        mBtnDetectPerson.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectPersonClicked();
            }
        });



        mTextViewCancelDetectPerson = (TextView) findViewById(R.id.textview_vision_cancelDetectPerson);
        mBtnCancelDetectPerson = (Button) findViewById(R.id.CancelDetectPerson);
        mBtnCancelDetectPerson.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDetectPersonClicked();
            }
        });


        String stringtemp = (String) mBtnDetectPerson.getText();
        stringtemp = stringtemp + "( 1 )";
        mBtnDetectPerson.setText(stringtemp);

        mBtnDetectPerson.setEnabled(true);
        mBtnCancelDetectPerson.setEnabled(false);
        mTextViewDetectPerson.setEnabled(true);
        mTextViewCancelDetectPerson.setEnabled(false);
    }

    private void detectPersonClicked() {
        robotAPI.vision.requestDetectPerson( 3000);


        mBtnDetectPerson.setEnabled(false);
        mBtnCancelDetectPerson.setEnabled(true);

        mTextViewDetectPerson.setEnabled(false);
        mTextViewCancelDetectPerson.setEnabled(true);
    }

    private void cancelDetectPersonClicked() {

        robotAPI.vision.cancelDetectPerson();

        mBtnDetectPerson.setEnabled(true);
        mBtnCancelDetectPerson.setEnabled(false);

        mTextViewDetectPerson.setEnabled(true);
        mTextViewCancelDetectPerson.setEnabled(false);
    }


    @Override
    protected void onPause() {
        super.onPause();

        robotAPI.vision.cancelDetectPerson();

        mBtnDetectPerson.setEnabled(true);
        mBtnCancelDetectPerson.setEnabled(false);

        mTextViewDetectPerson.setEnabled(true);
        mTextViewCancelDetectPerson.setEnabled(false);
    }


    @Override
    protected void onResume() {
        super.onResume();

        robotAPI.vision.cancelDetectPerson();

        mBtnDetectPerson.setEnabled(true);
        mBtnCancelDetectPerson.setEnabled(false);

        mTextViewDetectPerson.setEnabled(true);
        mTextViewCancelDetectPerson.setEnabled(false);

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
                API.robot.stopSpeak();
            }
            else {
                Log.d("RobotDevSample", "onDetectPersonResult: " + resultList.get(0).getBodyLoc().toString());
                Log.d("RobotDevSample", "resultList.size(): " + resultList.size());

                API.robot.speak("歡迎光臨");
                API.motion.moveBody(0,0,360, MotionControl.SpeedLevel.Body.L3);
                //API.robot.setExpression(RobotFace.HAPPY);
                //API.vision.cancelDetectPerson();
                //mContext.startActivity(intent);




                //use toast to show detect person
                String toast_result = String.valueOf(resultList.get(0));
                Toast toast = Toast.makeText(context, toast_result, Toast.LENGTH_SHORT);
                toast.show();
                resultList.clear();


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



    public VisionRequestDetectPerson() {
        super(robotCallback, robotListenCallback);
    }

}
