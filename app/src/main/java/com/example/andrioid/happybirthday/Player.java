package com.example.andrioid.happybirthday;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;


public class Player extends Activity {

    private Button buttonPlayStop;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;

    private final Handler handler = new Handler();

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {
        buttonPlayStop = (Button) findViewById(R.id.ButtonPlayStop);
        mediaPlayer = MediaPlayer.create(this, R.raw.song);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seekChange(v);
                return false;
            }
        });
    }

    private void seekChange(View v) {
        if (mediaPlayer.isPlaying()) {
            SeekBar sb = (SeekBar) v;
            mediaPlayer.seekTo(sb.getProgress());
        }
    }

    public void playAndStop(View v) {
        if (buttonPlayStop.getText() == getString(R.string.play_str)) {
            buttonPlayStop.setText(getString(R.string.pause_str));
            try {
                mediaPlayer.start();
                startPlayProgressUpdater();
            } catch (IllegalStateException e) {
                mediaPlayer.pause();
            }
        } else {
            buttonPlayStop.setText(getString(R.string.play_str));
            mediaPlayer.pause();
        }
    }

    public void startPlayProgressUpdater() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    startPlayProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        } else {
            mediaPlayer.pause();
            buttonPlayStop.setText(getString(R.string.play_str));
            seekBar.setProgress(0);
        }
    }


}


