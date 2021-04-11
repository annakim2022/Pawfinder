package com.example.pawfinder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class SecondActivity extends AppCompatActivity {
    private static AsyncHttpClient client = new AsyncHttpClient();
    private CheckBox checkBox_cat;
    private CheckBox checkBox_dog;
    private CheckBox checkBox_rabbit;
    private CheckBox checkBox_baby;
    private CheckBox checkBox_YA;
    private CheckBox checkBox_senior;
    private CheckBox checkBox_female;
    private CheckBox checkBox_male;
    private CheckBox checkBox_small;
    private CheckBox checkBox_medSize;
    private CheckBox checkBox_large;
    private CheckBox checkBox_XL;
    private CheckBox checkBox_short;
    private CheckBox checkBox_medCoat;
    private CheckBox checkBox_long;
    private CheckBox checkBox_wire;
    private CheckBox checkBox_hairless;
    private CheckBox checkBox_curly;
    private CheckBox checkBox_yesSpNd;
    private CheckBox checkBox_noSpNd;
    private Switch switch_declawed;
    private Switch switch_houseTrained;
    private Switch switch_gwChildren;
    private Switch switch_gwCats;
    private Switch switch_gwDogs;
    private SeekBar seekBar_distance;
    private Button button_find;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        checkBox_cat = findViewById(R.id.checkBox_cat);
        checkBox_dog = findViewById(R.id.checkBox_dog);
        checkBox_rabbit = findViewById(R.id.checkBox_rabbit);
        checkBox_baby = findViewById(R.id.checkBox_baby);
        checkBox_YA = findViewById(R.id.checkBox_YA);
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
        checkBox_yesSpNd = findViewById(R.id.checkBox_yesSpNd);
        checkBox_noSpNd = findViewById(R.id.checkBox_noSpNd);
        switch_declawed = findViewById(R.id.switch_declawed);
        switch_houseTrained = findViewById(R.id.switch_houseTrained);
        switch_gwChildren = findViewById(R.id.switch_gwChildren);
        switch_gwCats = findViewById(R.id.switch_gwCats);
        switch_gwDogs = findViewById(R.id.switch_gwDogs);
        seekBar_distance = findViewById(R.id.seekBar_distance);
        button_find = findViewById(R.id.button_find);

        button_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchNextActivity(v);
            }
        });

    }
    public void launchNextActivity(View v) {
        boolean catChecked = checkBox_cat.isChecked();
        boolean dogChecked = checkBox_dog.isChecked();
        boolean rabbitChecked = checkBox_rabbit.isChecked();
        boolean babyChecked = checkBox_baby.isChecked();
        boolean YAChecked = checkBox_YA.isChecked();
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
        boolean yesSpNdChecked = checkBox_yesSpNd.isChecked();
        boolean noSpNdChecked = checkBox_noSpNd.isChecked();
        boolean declawed = switch_declawed.isChecked();
        boolean houseTrained = switch_houseTrained.isChecked();
        boolean gwChildren = switch_gwChildren.isChecked();
        boolean gwCats = switch_gwCats.isChecked();
        boolean gwDogs = switch_gwDogs.isChecked();

        String api_url = "https://api.petfinder.com/v2/animals";

        client.get(api_url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });



    }
}
