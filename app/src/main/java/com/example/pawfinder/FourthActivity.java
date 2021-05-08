package com.example.pawfinder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FourthActivity extends AppCompatActivity implements SensorEventListener {

    private TextView textView_nameAct4, textView_typeAct4, textView_ageAct4, textView_genderAct4, textView_breedAct4, textView_sizeAct4, textView_colorAct4, textView_coatAct4,
                        textView_declawedAct4, textView_houseTrainedAct4, textView_specialNeedsAct4, textView_gwChildrenAct4, textView_gwCatsAct4, textView_gwDogsAct4;
    private ImageView imaageView_act4;
    private Button button_share, button_contactOrg;

    // brightness
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private String org;
    private String token;

    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        // brightness
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        textView_nameAct4 = findViewById(R.id.textView_nameAct4);
        textView_typeAct4 = findViewById(R.id.textView_typeAct4);
        textView_ageAct4 = findViewById(R.id.textView_ageAct4);
        textView_genderAct4 = findViewById(R.id.textView_genderAct4);
        textView_breedAct4 = findViewById(R.id.textView_breedAct4);
        textView_sizeAct4 = findViewById(R.id.textView_sizeAct4);
        textView_colorAct4 = findViewById(R.id.textView_colorAct4);
        textView_coatAct4 = findViewById(R.id.textView_coatAct4);
        textView_declawedAct4 = findViewById(R.id.textView_declawedAct4);
        textView_houseTrainedAct4 = findViewById(R.id.textView_houseTrainedAct4);
        textView_specialNeedsAct4 = findViewById(R.id.textView_specialNeedsAct4);
        textView_gwChildrenAct4 = findViewById(R.id.textView_gwChildrenAct4);
        textView_gwCatsAct4 = findViewById(R.id.textView_gwCatsAct4);
        textView_gwDogsAct4 = findViewById(R.id.textView_gwDogsAct4);
        imaageView_act4 = findViewById(R.id.imageView_act4);

        button_share = findViewById(R.id.button_share);
        button_contactOrg = findViewById(R.id.button_contactOrg);


        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchFifthAct();
            }
        });

        button_contactOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactOrganization();
            }
        });

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        String id = intent.getStringExtra("pet");
        org = intent.getStringExtra("org");

        String api_url = "https://api.petfinder.com/v2/animals/" + id;

        client.addHeader("Accept", "application/json");
        client.addHeader("Authorization", "Bearer " + token);
        Log.d("im not sure", api_url);
        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));
                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    // type, age, gender, breed, size, color, coat, declawed, house trained, special needs, good with children, good with cats, good with dogs, location
                    JSONObject animal = json.getJSONObject("animal");

                    String name = animal.getString("name");
                    textView_nameAct4.setText(name);

                    JSONArray photo_arr = animal.getJSONArray("photos");
                    if(photo_arr.length() > 0){
                        JSONObject photo_obj = photo_arr.getJSONObject(0);
                        String photo_string = photo_obj.getString("small");
                        if(photo_string != null){
                            Picasso.get().load(photo_string).into(imaageView_act4);
                        }
                    }

                    // type info
                    String type = animal.getString("type");
                    textView_typeAct4.setText("Type: " + type);

                    // age info
                    String age = animal.getString("age");
                    textView_ageAct4.setText("Age: " + age);

                    // gender info
                    String gender = animal.getString("gender");
                    textView_genderAct4.setText("Gender: " + gender);

                    // breed -> primary info (there are more breeds, can fix later if want to)
                    JSONObject breed_obj = animal.getJSONObject("breeds");
                    String breed = breed_obj.getString("primary");
                    textView_breedAct4.setText("Primary Breed: " + breed);

                    // size info
                    String size = animal.getString("size");
                    textView_sizeAct4.setText("Size: " + size);

                    // color -> primary info (there is more than one color)
                    JSONObject color_obj =  animal.getJSONObject("colors");
                    String color = color_obj.getString("primary");
                    textView_colorAct4.setText("Primary Color: " + color);

                    // coat info
                    String coat = animal.getString("coat");
                    textView_coatAct4.setText("Coat: " + coat);

                    // getting object attributes
                    JSONObject attributes = animal.getJSONObject("attributes");

                    // declawed info
                    String declawed = attributes.getString("declawed");
                    if(declawed == "null"){ textView_declawedAct4.setText("Declawed: No");
                    }else{ textView_declawedAct4.setText("Declawed: Yes"); }

                    // house_trained info
                    String house_trained = attributes.getString("house_trained");
                    if(house_trained == "false"){ textView_houseTrainedAct4.setText("House Trained: No");
                    }else{ textView_houseTrainedAct4.setText("House Trained: Yes"); }

                    // special_needs info
                    String special_needs = attributes.getString("special_needs");
                    if(special_needs == "true"){ textView_specialNeedsAct4.setText("Special Needs: Yes");
                    }else{ textView_specialNeedsAct4.setText("Speical Needs: No"); }

                    // getting object environment
                    JSONObject environment = animal.getJSONObject("environment");

                    // good with children info
                    String children = environment.getString("children");
                    if(children == "true"){ textView_gwChildrenAct4.setText("Good with Children: Yes");
                    }else{ textView_gwChildrenAct4.setText("Good with Children: No"); }

                    // good with dogs info
                    String dogs = environment.getString("dogs");
                    if(dogs == "true"){ textView_gwDogsAct4.setText("Good with Dogs: Yes");
                    }else{ textView_gwDogsAct4.setText("Good with Dogs: No"); }

                    // good with cats info
                    String cats = environment.getString("cats");
                    if(cats == "true"){ textView_gwCatsAct4.setText("Good with Cats: Yes");
                    }else{ textView_gwCatsAct4.setText("Good with Cats: No"); }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
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

    public void launchFourthAct(){
        Intent intent = new Intent(this, FourthActivity.class);
        startActivity(intent);
    }


    public void launchFifthAct(){
        Intent intent = new Intent(this, FifthActivity.class);

        String name_intent = textView_nameAct4.getText().toString();
        intent.putExtra("name", name_intent);

        String type_intent = textView_typeAct4.getText().toString();
        intent.putExtra("type", type_intent);

        startActivity(intent);
    }

    public void contactOrganization(){

            String api_url_2 = "https://api.petfinder.com/v2/organizations/" + org;
            System.out.println("THI SI SM ET ESTING THE URL " + api_url_2);

            client.addHeader("Accept", "application/json");
            client.addHeader("Authorization", "Bearer " + token);
            client.get(api_url_2, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.d("api response", new String(responseBody));
                    try {

                        JSONObject json = new JSONObject(new String(responseBody));
                        JSONObject organization = json.getJSONObject("organization");
                        String url = organization.getString("url");
                        System.out.println(url);

                        createNotificationChannel();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        PendingIntent pendingIntent = PendingIntent.getActivity(FourthActivity.this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(FourthActivity.this, "CHANNEL_ID")
                                .setAutoCancel(true)
                                .setSmallIcon(R.drawable.redlightsaber)
                                .setContentTitle("Contact organization!")
                                .setContentText("Click here to learn more:")
                                .setContentIntent(pendingIntent)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(FourthActivity.this);

                        notificationManager.notify(100, builder.build());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("api error", new String(responseBody));
                }
            });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name";
            String description = "dscription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
