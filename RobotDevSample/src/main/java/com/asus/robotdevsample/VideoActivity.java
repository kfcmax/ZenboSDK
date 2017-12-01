package com.asus.robotdevsample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        Uri uri = Uri.parse("https://www.youtube.com/playlist?list=PLDNSbUVwzza36H7VDvG_pu8Vc7xlOcAdS"); ///////////如何全螢幕 將watch?v=  刪除 加上 embed/，最後加上 ?autoplay=1
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
       // intent.putExtra("force_fullscreen",true);
        startActivity(intent);
//        Uri uri = Uri.parse("https://www.youtube.com/embed/4d2R7c3YcBg?autoplay=1"); ///////////如何全螢幕 將watch?v=  刪除 加上 embed/，最後加上 ?autoplay=1
//        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
//        startActivity(intent);

        //_popup
    }
}
