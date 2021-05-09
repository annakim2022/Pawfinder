package com.example.pawfinder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SecondActivity extends AppCompatActivity implements SensorEventListener {
    private static AsyncHttpClient client = new AsyncHttpClient();

    private CheckBox checkBox_cat, checkBox_dog, checkBox_rabbit, checkBox_baby, checkBox_young, checkBox_adult, checkBox_senior, checkBox_female,
            checkBox_male, checkBox_small, checkBox_medSize, checkBox_large, checkBox_XL, checkBox_short, checkBox_medCoat,
            checkBox_long, checkBox_wire, checkBox_hairless, checkBox_curly;

    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJsajg2ekZPU0k3YTB3QVlEZUR0WVBLcUxodVdEcFE5UUN3bUxlejB6d0FpdmFTSVUzcyIsImp0aSI6IjJmN2NlMTE4ZGQ2MzRiODY2NDI3ZGI1ZjliYWZmZmMyNWMzNTI5MTFmODI2YWU2MzhlODA5YTk0ZDM2YjRlYWVhYWJiMDU0OTJiMTc3NzNiIiwiaWF0IjoxNjIwNjAyNTIyLCJuYmYiOjE2MjA2MDI1MjIsImV4cCI6MTYyMDYwNjEyMiwic3ViIjoiIiwic2NvcGVzIjpbXX0.fbtS1PxW2NKKU3deWxCb-ALjks5lSm9aZDE0ggcrheXO4r1FijVov_ppw5-gi8W8X3cwp_Zeo6pCxRebyw9anVRDcIy_a0s81hntFm421fppORRwvRpu6JKnI_nzrjMazh4NXntZZRNFKv0HxErA-c-JkEgLbg6aRNypRqRpxkiYsSHJNlf4wO9pOYF70N2wyxNtxsJfbOmSZqF9a1zolQqi522cWH24kuVfi5A81q1ZVN67duHhm59qnXvf081Joy3DobXztSqAMJ-7ZBwM6_fDtazlp1Bgr8bCVvEvBNLRhhI5d8RZZZ1RQSuwTdB8f8BXiP__PH1rc3AP4y-X9Q";

    private Switch switch_declawed, switch_houseTrained, switch_gwChildren, switch_gwCats, switch_gwDogs, switch_specialNeeds;
    private SeekBar seekBar;
    private Button button_find;
    private TextView textView_seekbarDistance;
    private TextView textView_location;

    private List<String> list_id;
    private List<String> list_org;
    private List<String> list_type;
    private List<String> list_name;
    private List<String> list_age;
    private List<String> list_gender;
    private List<String> list_photos;
    private List<String> list_distance;

    // brightness
    private SensorManager sensorManager;
    private Sensor lightSensor;

    private FusedLocationProviderClient fusedLocationClient;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        checkBox_cat = findViewById(R.id.checkBox_cat);
        checkBox_dog = findViewById(R.id.checkBox_dog);
        checkBox_rabbit = findViewById(R.id.checkBox_rabbit);
        checkBox_baby = findViewById(R.id.checkBox_baby);
        checkBox_young = findViewById(R.id.checkBox_young);
        checkBox_adult = findViewById(R.id.checkBox_adult);
        checkBox_senior = findViewById(R.id.checkBox_senior);
        checkBox_female = findViewById(R.id.checkBox_female);
        checkBox_male = findViewById(R.id.checkBox_male);
        checkBox_small = findViewById(R.id.checkBox_small);
        checkBox_medSize = findViewById(R.id.checkBox_medSize);
        checkBox_large = findViewById(R.id.checkBox_large);
        checkBox_XL = findViewById(R.id.checkBox_XL);
        checkBox_short = findViewById(R.id.checkBox_short);
        checkBox_medCoat = findViewById(R.id.checkBox_medCoat);
        checkBox_long = findViewById(R.id.checkBox_long);
        checkBox_wire = findViewById(R.id.checkBox_wire);
        checkBox_hairless = findViewById(R.id.checkBox_hairless);
        checkBox_curly = findViewById(R.id.checkBox_curly);
        switch_specialNeeds = findViewById(R.id.switch_specialNeeds);
        switch_declawed = findViewById(R.id.switch_declawed);
        switch_houseTrained = findViewById(R.id.switch_houseTrained);
        switch_gwChildren = findViewById(R.id.switch_gwChildren);
        switch_gwCats = findViewById(R.id.switch_gwCats);
        switch_gwDogs = findViewById(R.id.switch_gwDogs);
        seekBar = findViewById(R.id.seekBar_distance);
        button_find = findViewById(R.id.button_find);

        // location
        textView_location = findViewById(R.id.textView_userLocation);
        try {
            textView_location.setText(getLastLocation());
        }
        catch (Exception e){
        //Toast.makeText(SecondActivity.this, "plsllslslslslslsls workrkkrkrkrkrkrk", Toast.LENGTH_LONG).show();
        }

        // distance
        textView_seekbarDistance = findViewById(R.id.textView_seekbarDistance);
        textView_seekbarDistance.setText("100 mi");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // brightness
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


        seekFunction();

        button_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checked = 0;
                if (checkBox_cat.isChecked()){
                    checked = checked + 1;
                }
                if (checkBox_dog.isChecked()){
                    checked = checked + 1;
                }
                if (checkBox_rabbit.isChecked()){
                    checked = checked + 1;
                }
                if (checked > 1){
                    Toast.makeText(SecondActivity.this, "please only check 1 pet type", Toast.LENGTH_LONG).show();
                }
                else {
                    launchNextActivity(v);
                }
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

//// trying to commit
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void launchNextActivity(View v) {

        String api_url = "https://api.petfinder.com/v2/animals?";

        boolean catChecked = checkBox_cat.isChecked();
        boolean dogChecked = checkBox_dog.isChecked();
        boolean rabbitChecked = checkBox_rabbit.isChecked();
        // for these three ^^ if all or more than one checked only returns one, should have something that says that/ confirms it
        // should have something that says pick one

        boolean babyChecked = checkBox_baby.isChecked();
        boolean youngChecked = checkBox_young.isChecked();
        boolean adultChecked = checkBox_adult.isChecked();
        boolean seniorChecked = checkBox_senior.isChecked();
        boolean femaleChecked = checkBox_female.isChecked();
        boolean maleChecked = checkBox_male.isChecked();
        boolean smallChecked = checkBox_small.isChecked();
        boolean medChecked = checkBox_medSize.isChecked();
        boolean largeChecked = checkBox_large.isChecked();
        boolean XLChecked = checkBox_XL.isChecked();
        boolean shortChecked = checkBox_short.isChecked();
        boolean medHairChecked = checkBox_medCoat.isChecked();
        boolean longChecked = checkBox_long.isChecked();
        boolean wireChecked = checkBox_wire.isChecked();
        boolean hairlessChecked = checkBox_hairless.isChecked();
        boolean curlyChecked = checkBox_curly.isChecked();
        //   boolean noSpNdChecked = checkBox_noSpNd.isChecked();
        // not sure if this one is necessary
        // some of the things only have a true option
        boolean yesSpNdChecked =  switch_specialNeeds.isChecked();
        boolean declawed = switch_declawed.isChecked();
        boolean houseTrained = switch_houseTrained.isChecked();
        boolean gwChildren = switch_gwChildren.isChecked();
        boolean gwCats = switch_gwCats.isChecked();
        boolean gwDogs = switch_gwDogs.isChecked();
        int checked = 0;

        if (catChecked) {
            api_url = api_url + "type=cat&";
        }
        if (dogChecked) {
            api_url = api_url + "type=dog&";
        }
        if (rabbitChecked) {
            api_url = api_url + "type=rabbit&";
        }
        if (babyChecked) {
            api_url = api_url + "age=baby&";
        }
        if (youngChecked) {
            api_url = api_url + "age=young&";
        }
        if (adultChecked) {
            api_url = api_url + "age=adult&";
        }
        if (seniorChecked) {
            api_url = api_url + "age=senior&";
        }
        if (femaleChecked) {
            api_url = api_url + "gender=female&";
        }
        if (maleChecked) {
            api_url = api_url + "gender=male&";
        }
        if (smallChecked) {
            api_url = api_url + "size=small&";
        }
        if (medChecked) {
            api_url = api_url + "size=medium&";
        }
        if (largeChecked) {
            api_url = api_url + "size=large&";
        }
        if (XLChecked) {
            api_url = api_url + "size=xlarge&";
        }
        if (shortChecked) {
            api_url = api_url + "coat=short&";
        }
        if (medHairChecked) {
            api_url = api_url + "coat=medium&";
        }
        if (longChecked) {
            api_url = api_url + "coat=long&";
        }
        if (wireChecked) {
            api_url = api_url + "coat=wire&";
        }
        if (hairlessChecked) {
            api_url = api_url + "coat=hairless&";
        }
        if (curlyChecked) {
            api_url = api_url + "coat=curly&";
        }
        if (yesSpNdChecked) {
            api_url = api_url + "special_needs=1&";
        }
        //   if(noSpNdChecked){ api_url = api_url + "special_needs&"; }
        if (declawed) {
            api_url = api_url + "declawed=1&";
        }
        if (houseTrained) {
            api_url = api_url + "house_trained=1&";
        }
        if (gwChildren) {
            api_url = api_url + "good_with_children=1&";
        }
        if (gwCats) {
            api_url = api_url + "good_with_cats=1&";
        }
        if (gwDogs) {
            api_url = api_url + "good_with_dogs=1&";
        }

        // get location
        // get distance
        //api_url = api_url + "location=" + getLastLocation() + "&";
        String location_fix = textView_seekbarDistance.getText().toString().replace(" mi", "");
        api_url = api_url + "location=" + textView_location.getText().toString() + "&";
        api_url = api_url + "distance=" + location_fix + "&";

        client.addHeader("Accept", "application/json");
        client.addHeader("Authorization", "Bearer " + token);
        Log.d("im not sure", api_url);
        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));
                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                    intent.putExtra("token", token);

                    list_id = new ArrayList<String>();
                    list_org = new ArrayList<String>();
                    list_type = new ArrayList<String>();
                    list_name = new ArrayList<String>();
                    list_age = new ArrayList<String>();
                    list_gender = new ArrayList<String>();
                    list_photos = new ArrayList<String>();
                    list_distance = new ArrayList<String>();

                    JSONArray animal = json.getJSONArray("animals");

                    for (int i = 0; i < animal.length(); i++) {
                        JSONObject temp = animal.getJSONObject(i);

                        String id = temp.getString("id");
                        list_id.add(id);

                        String org = temp.getString("organization_id");
                        list_org.add(org);

                        String type = temp.getString("type");
                        list_type.add(type);

                        String name = temp.getString("name");
                        list_name.add(name);

                        String age = temp.getString("age");
                        list_age.add(age);

                        String gender = temp.getString("gender");
                        list_gender.add(gender);

                        String distance = temp.getString("distance");
                        list_distance.add(distance);


                        JSONArray ph_arr = temp.getJSONArray("photos");
                        if (ph_arr.length() > 0) {
                            JSONObject te = ph_arr.getJSONObject(0);
                            String testing = te.getString("small");
                            System.out.println("this is testing " + testing);
                            list_photos.add(testing);
                        } else {
                            list_photos.add("https://www.dogster.com/wp-content/uploads/2015/05/dog-http-status-codes-404.jpg");
                        }
                    }

                    intent.putStringArrayListExtra("id", (ArrayList<String>) list_id);
                    intent.putStringArrayListExtra("org", (ArrayList<String>) list_org);
                    intent.putStringArrayListExtra("type", (ArrayList<String>) list_type);
                    intent.putStringArrayListExtra("name", (ArrayList<String>) list_name);
                    intent.putStringArrayListExtra("age", (ArrayList<String>) list_age);
                    intent.putStringArrayListExtra("gender", (ArrayList<String>) list_gender);
                    intent.putStringArrayListExtra("distance", (ArrayList<String>) list_distance);
                    intent.putStringArrayListExtra("photo", (ArrayList<String>) list_photos);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }

    @SuppressLint("MissingPermission")
    private String getLastLocation() {
        final String[] userLocation = new String[1];
        // check if permissions are given
        if (checkPermissions()) {
            // check if location is enabled
            if (isLocationEnabled()) {
                fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            textView_location.setText(location.getLatitude() + ", " + location.getLongitude());
                            userLocation[0] = location.getLatitude() + ", " + location.getLongitude();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
        return userLocation[0];
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            textView_location.setText(mLastLocation.getLatitude() + ", " + mLastLocation.getLongitude());
        }
    };

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, 44);
    }
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }



    public void seekFunction(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView_seekbarDistance.setText(progress + " mi");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }




}

