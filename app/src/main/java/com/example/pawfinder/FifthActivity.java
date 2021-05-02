package com.example.pawfinder;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FifthActivity extends AppCompatActivity implements SensorEventListener {

    private Button button_backAct5, button_backToMain, button_shareAct5;
    private TextView textView_phoneText, textView_message;
    private EditText editText_phone;

    // sensor
    private SensorManager sensorManager;
    private Sensor lightSensor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);

        // brightness
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

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
                    Toast.makeText(FifthActivity.this, "failed", Toast.LENGTH_SHORT).show();
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
    @Override
    public void onSensorChanged(SensorEvent event) {

        // called when new sensor data is available
        // you will use this callback most often to handle new sensor data in your app
        // example: detects a temp change, based on the new sensor data, change the icon/background of your activity
        // get the change
        // display the change

        int sensorType = event.sensor.getType();
        // grab the new data of the sensor
        if (sensorType == Sensor.TYPE_LIGHT) {
            float currentValue = event.values[0];

            // methods you can use to grab the maximum value of the range
            if (currentValue <= lightSensor.getMaximumRange()/3){
                // turn on "dark mode"
                changeScreenBrightness(this, 55);
            }
            if (currentValue > lightSensor.getMaximumRange()/3 && currentValue <= (lightSensor.getMaximumRange()*2)/3){
                // turn on "dark mode"
                changeScreenBrightness(this, 155);
            }
            if (currentValue > (lightSensor.getMaximumRange()*2)/3){
                // turn on "dark mode"
                changeScreenBrightness(this, 255);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // called if the sensor's accuracy is changed so that  your app can react to that change
        // most of the sensors do not report accuracy changes so most of the time you leave this callback empty

    }

    private void changeScreenBrightness (Context context,int screenBrightnessValue)
    {
        // Change the screen brightness change mode to manual.
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        // Apply the screen brightness value to the system, this will change the value in Settings ---> Display ---> Brightness level.
        // It will also change the screen brightness for the device.
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, screenBrightnessValue);


    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
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
