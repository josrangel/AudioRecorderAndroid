package com.josrangel.audiorecorder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


/**
 * @author josrangel
 * @tutorialOriginal: https://tutorialesprogramacionya.com/javaya/androidya/androidstudioya/detalleconcepto.php?codigo=28
 */
public class MainActivity extends AppCompatActivity {

    private int peticion, duration;
    private MediaPlayer mediaPlayer;
    private Uri url1;
    private Button btnRecord, btnPlay;
    private TextView txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRecord = findViewById(R.id.btnRecord);
        btnPlay = findViewById(R.id.btnPlay);
        txtStatus = findViewById(R.id.txtStatus);
    }

    public void recorder(View v) {
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, peticion);
    }

    public void play(View v) {
        if (mediaPlayer != null) {
            mediaPlayer = MediaPlayer.create(this, url1);
            mediaPlayer.start();
        } else {
            Toast.makeText(this,R.string.without_record, Toast.LENGTH_SHORT).show();
        }
    }

    private void enableButtons(boolean active) {
        btnRecord.setEnabled(active);
        btnPlay.setEnabled(active);
    }

    private void showStatus(String status) {
        txtStatus.setText(status);
    }

    private String timeInString() {
        String time = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
        );
        return time;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == peticion) {
            url1 = data.getData();
            mediaPlayer = MediaPlayer.create(this, url1);
            duration = mediaPlayer.getDuration();
            showStatus(timeInString());
        }
    }
}