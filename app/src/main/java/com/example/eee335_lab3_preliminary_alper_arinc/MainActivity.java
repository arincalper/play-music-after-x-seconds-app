package com.example.eee335_lab3_preliminary_alper_arinc;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText seconds = (EditText) findViewById(R.id.et_input);
        TextView left = (TextView) findViewById(R.id.tv_left) ;
        Button ok_button = (Button) findViewById(R.id.button);
        final MediaPlayer music = MediaPlayer.create(this, R.raw.playmusic);

        ok_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0); // I thought the music was not playing but the reality was, the audio volume was set to zero initially. So I had to write 2 lines of code to set the volume up.




                new Thread() {
                    public void run() {

                        try {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String input = seconds.getText().toString();
                                    int input2 = Integer.parseInt(String.valueOf(Integer.parseInt(input) * 1000 + 500));


                                    new CountDownTimer(input2, 1000) {


                                        public void onTick(long millisUntilFinished) {

                                            // Used for formatting digit to be in 2 digits only
                                            int input2 = Integer.parseInt(String.valueOf(Integer.parseInt(input) * 1000));

                                            NumberFormat f = new DecimalFormat("00");
                                            long hour = (millisUntilFinished / 3600000) % 24;

                                            long min = (millisUntilFinished / 60000) % 60;


                                            long sec = (millisUntilFinished / 1000) % 60;

                                            left.setText("Left: " +f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));


                                        }

                                        // When the task is over it will print 00:00:00 there

                                        public void onFinish() {

                                            left.setText("00:00:00");
                                            Toast.makeText(getApplicationContext(), "Music is starting...", Toast.LENGTH_LONG).show();
                                            music.start();


                                        }

                                    }.start();

                                }
                            });
                            Thread.sleep(0);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            }

        });
    }
}