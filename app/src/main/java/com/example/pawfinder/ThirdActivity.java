package com.example.pawfinder;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ThirdActivity extends AppCompatActivity implements SensorEventListener{


    private ImageView imageView_pet;
    private TextView textView_typeAct3, textView_nameAct3, textView_ageAct3, textView_genderAct3, textView_distanceAct3;
    private Button button_next, button_moreInfo;

    private int counter = 0;
    private List<String> id, org, type, name, age, gender, distance, photos;

    private String token;

    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    // brightness
    private SensorManager sensorManager;
    private Sensor lightSensor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // brightness
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        imageView_pet = findViewById(R.id.imageView_pet);
        textView_typeAct3 = findViewById(R.id.textView_typeAct3);
        textView_nameAct3 = findViewById(R.id.textView_nameAct3);
        textView_ageAct3 = findViewById(R.id.textView_ageAct3);
        textView_genderAct3 = findViewById(R.id.textView_genderAct3);
        textView_distanceAct3 = findViewById(R.id.textView_distanceAct3);
        button_next = findViewById(R.id.button_next);
        button_moreInfo = findViewById(R.id.button_moreInfo);

        id = new ArrayList<String>();
        org = new ArrayList<String>();
        type = new ArrayList<String>();
        name = new ArrayList<String>();
        age = new ArrayList<String>();
        gender = new ArrayList<String>();
        photos = new ArrayList<String>();
        distance = new ArrayList<String>();

        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        id = intent.getStringArrayListExtra("id");
        org = intent.getStringArrayListExtra("org");
        type = intent.getStringArrayListExtra("type");
        name = intent.getStringArrayListExtra("name");
        age = intent.getStringArrayListExtra("age");
        gender = intent.getStringArrayListExtra("gender");
        distance = intent.getStringArrayListExtra("distance");
        photos = intent.getStringArrayListExtra("photo");

        textView_typeAct3.setText("Type: " + type.get(0));
        textView_nameAct3.setText("Name: " + name.get(0));
        textView_ageAct3.setText("Age: " + age.get(0));
        textView_genderAct3.setText("Gender: " + gender.get(0));

        if(distance.get(0).isEmpty()){
            textView_distanceAct3.setText("N/A");
        }else {
            textView_distanceAct3.setText("Distance: " + distance.get(0) + " mi");
        }
        Picasso.get().load(photos.get(0)).into(imageView_pet);


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter < type.size()) {
                    counter++;
                }
                else {
                    Toast.makeText(ThirdActivity.this, "No more pets!", Toast.LENGTH_LONG).show();
                }
                nextPet(type, name, age, gender, distance, photos, counter);

            }
        });

        button_moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(counter);
              //  if(counter == id.size()){
                  // counter --;
            //    }
                launchNextActivity(counter, id, org, photos);
            }
        });

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

    }

    // implement shake to undo

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 12) {
                counter --;
                if(counter < 0){
                    Toast.makeText(getApplicationContext(), "You are back on the first pet", Toast.LENGTH_LONG).show();
                }else {
                    backPet(type, name, age, gender, distance, photos, counter);
                }
              //  Toast.makeText(getApplicationContext(), "Shake event detected", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    @Override
    protected void onResume() {
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        sensorManager.unregisterListener(this);
        super.onPause();
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

            // range of these sensors are different from each other
            // 0 to 10000
            // 0 to 30000

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



    public void nextPet(List<String> type, List<String> name, List<String> age, List<String> gender, List<String> distance, List<String> photos, int counter){
       try {
           textView_typeAct3.setText("Type: " + type.get(counter));
           textView_nameAct3.setText("Name: " + name.get(counter));
           textView_ageAct3.setText("Age: " + age.get(counter));
           textView_genderAct3.setText("Gender: " + gender.get(counter));
           if (distance.get(counter) == null) {
               textView_distanceAct3.setText("N/A");
           } else {
               textView_distanceAct3.setText("Distance: " + distance.get(counter) + " mi");
           }
           Picasso.get().load(photos.get(counter)).into(imageView_pet);
       }catch(Exception e){
           //Toast.makeText(ThirdActivity.this, "No more pets!", Toast.LENGTH_LONG).show();
       }

    }

    public void backPet(List<String> type, List<String> name, List<String> age, List<String> gender, List<String> distance, List<String> photos, int counter){
        textView_typeAct3.setText("Type: " + type.get(counter));
        textView_nameAct3.setText("Name: " + name.get(counter));
        textView_ageAct3.setText("Age: " + age.get(counter));
        textView_genderAct3.setText("Gender: " + gender.get(counter));
        if(distance.get(counter) == null){
            textView_distanceAct3.setText("N/A");
        }else {
            textView_distanceAct3.setText("Distance: " + distance.get(counter) + " mi");
        }
        Picasso.get().load(photos.get(counter)).into(imageView_pet);
    }



    public void launchNextActivity(int counter, List<String> id, List<String> org, List<String> photos){
        String pet = id.get(counter);
        String org_1 = org.get(counter);
        String photo_string = photos.get(counter);
        Intent intent = new Intent(this, FourthActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("pet", pet);
        intent.putExtra("org", org_1);
        intent.putExtra("photo", photo_string);
        System.out.println("THIS IS THE PET ID " + pet);
        System.out.println("This is the org" + org_1);
        startActivity(intent);
    }


}
