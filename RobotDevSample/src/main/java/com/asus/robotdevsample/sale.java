package com.asus.robotdevsample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class sale extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        Uri uri = Uri.parse("https://www.youtube.com/watch?v=4d2R7c3YcBg"); ///////////如何全螢幕 將watch?v=  刪除 加上 embed/，最後加上 ?autoplay=1
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.putExtra("force_fullscreen",true);
        startActivity(intent);
    }
}
//https://www.youtube.com/embed/4d2R7c3YcBg?autoplay=1     https://youtu.be/4d2R7c3YcBg