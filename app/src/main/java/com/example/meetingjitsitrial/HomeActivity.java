package com.example.meetingjitsitrial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class HomeActivity extends AppCompatActivity {

    EditText secretCodeHomeScreen;
    Button joinConferenceBtn;

    FloatingActionButton shareConferenceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        secretCodeHomeScreen = findViewById(R.id.secretCodeHomeScreen);
        joinConferenceBtn = findViewById(R.id.joinConferenceBtn);
        shareConferenceBtn = findViewById(R.id.shareConferenceVia);

        URL serverURL;
        try {
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions=
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverURL)
                            .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }


        joinConferenceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(secretCodeHomeScreen.getText().toString())
                        .build();

                JitsiMeetActivity.launch(HomeActivity.this,options);
            }
        });

        shareConferenceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,"Conference code: "+secretCodeHomeScreen.getText().toString() + "\nDownload  app via playstore");
                startActivity(Intent.createChooser(shareIntent,"Share via"));
            }
        });
    }
}