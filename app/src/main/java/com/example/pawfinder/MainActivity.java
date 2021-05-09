package com.example.pawfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Button button_start;

    private SensorManager sensorManager;
    private Sensor lightSensor;
    // private TextView textView;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // textView = findViewById(R.id.textView);
        image = findViewById(R.id.imageView);
        image.setImageResource(R.drawable.cat);

        // sensor for brightness
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
/*
        if (lightSensor == null) {
            textView.setText("Light sensor not found.");
        }
 */

        button_start = findViewById(R.id.button_start);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchNextActivity(v);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        // register our sensor listeners

        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // unregister all the listeners so that they do not gather information when the app is paused
        // good for resource purpose and privacy purpose
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        boolean settingsCanWrite = hasWriteSettingsPermission(this);
        // If do not have then open the Can modify system settings panel.
        if(!settingsCanWrite) {
            changeWriteSettingsPermission(this);
        }
        else {

            int sensorType = event.sensor.getType();
            // grab the new data of the sensor
            if (sensorType == Sensor.TYPE_LIGHT) {
                float currentValue = event.values[0];
                //textView.setText("light sensor value " + currentValue + "\nmax range: " + lightSensor.getMaximumRange());

                // light range
                if (currentValue <= lightSensor.getMaximumRange()/3){
                    changeScreenBrightness(this, 55);
                }
                if (currentValue > lightSensor.getMaximumRange()/3 && currentValue <= (lightSensor.getMaximumRange()*2)/3){
                    changeScreenBrightness(this, 155);
                }
                if (currentValue > (lightSensor.getMaximumRange()*2)/3){
                    changeScreenBrightness(this, 255);
                }
            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // called if the sensor's accuracy is changed so that  your app can react to that change
        // most of the sensors do not report accuracy changes so most of the time you leave this callback empty

    }

    public void launchNextActivity(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);

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


    // Check whether this app has android write settings permission.
    private boolean hasWriteSettingsPermission (Context context){
        boolean ret = true;
        // Get the result from below code.
        ret = Settings.System.canWrite(context);
        return ret;
    }

    // Start can modify system settings panel to let user change the write settings permission.
    private void changeWriteSettingsPermission (Context context)
    {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        context.startActivity(intent);
    }


}