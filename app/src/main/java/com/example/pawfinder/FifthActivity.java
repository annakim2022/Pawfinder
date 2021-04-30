package com.example.pawfinder;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FifthActivity extends AppCompatActivity {

    private Button button_backAct5, button_backToMain, button_shareAct5;
    private TextView textView_phoneText, textView_message;
    private EditText editText_phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);

        button_backAct5 = findViewById(R.id.button_backAct5);
        button_backToMain = findViewById(R.id.button_backToMain);
        button_shareAct5 = findViewById(R.id.button_shareAct5);
        textView_phoneText = findViewById(R.id.textView_phoneText);
        textView_message = findViewById(R.id.textView_message);
        editText_phone = findViewById(R.id.editText_phone);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String type = intent.getStringExtra("type");

        String type_updated = type.replace("Type: ", "");

        textView_message.setText("Message: Hey, check out this " + type_updated + "! His/Her name is " + name + " and I'm " +
                "thinking about adopting him/her. Let me know what you think!");
        // just a thought we can also send the gender as an intent ^^ later problem

        button_shareAct5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    SmsManager smgr = SmsManager.getDefault();
                    smgr.sendTextMessage(editText_phone.getText().toString(), null, textView_message.getText().toString(), null, null);
                    Toast.makeText(FifthActivity.this, "SMS SENT YAY", Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Toast.makeText(FifthActivity.this, "Failed to send, check entered phone number", Toast.LENGTH_SHORT).show();
                }


            }
        });

        button_backAct5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToFourth();
            }
        });
        button_backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });


    }

    private void backToFourth(){
        Intent intent = new Intent(this, FourthActivity.class);
        startActivity(intent);
    }
    private void backToMain(){
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }


}
