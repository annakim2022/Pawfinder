package com.example.pawfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SecondActivity extends AppCompatActivity {
    private static AsyncHttpClient client = new AsyncHttpClient();

    private CheckBox checkBox_cat, checkBox_dog, checkBox_rabbit, checkBox_baby, checkBox_young, checkBox_adult, checkBox_senior, checkBox_female,
                    checkBox_male, checkBox_small, checkBox_medSize, checkBox_large, checkBox_XL, checkBox_short, checkBox_medCoat,
                    checkBox_long, checkBox_wire, checkBox_hairless, checkBox_curly, checkBox_yesSpNd, checkBox_noSpNd;

    private Switch switch_declawed, switch_houseTrained, switch_gwChildren, switch_gwCats, switch_gwDogs;
    private SeekBar seekBar_distance;
    private Button button_find;

    private List<String> list_id;
    private List<String> list_type;
    private List<String> list_name;
    private List<String> list_age;
    private List<String> list_gender;
    private List<String> list_photos;


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
        boolean yesSpNdChecked = checkBox_yesSpNd.isChecked();
     //   boolean noSpNdChecked = checkBox_noSpNd.isChecked();
        // not sure if this one is necessary
        // some of the things only have a true option
        boolean declawed = switch_declawed.isChecked();
        boolean houseTrained = switch_houseTrained.isChecked();
        boolean gwChildren = switch_gwChildren.isChecked();
        boolean gwCats = switch_gwCats.isChecked();
        boolean gwDogs = switch_gwDogs.isChecked();

        if(catChecked){ api_url = api_url + "type=cat&"; }
        if(dogChecked){ api_url = api_url + "type=dog&"; }
        if(rabbitChecked){ api_url = api_url + "type=rabbit&"; }
        if(babyChecked){ api_url = api_url + "age=baby&"; }
        if(youngChecked){ api_url = api_url + "age=young&"; }
        if(adultChecked){ api_url = api_url + "age=adult&"; }
        if(seniorChecked){ api_url = api_url + "age=senior&"; }
        if(femaleChecked){ api_url = api_url + "gender=female&"; }
        if(maleChecked){ api_url = api_url + "gender=male&"; }
        if(smallChecked){ api_url = api_url + "size=small&"; }
        if(medChecked){ api_url = api_url + "size=medium&"; }
        if(largeChecked){ api_url = api_url + "size=large&"; }
        if(XLChecked){ api_url = api_url + "size=xlarge&"; }
        if(shortChecked){ api_url = api_url + "coat=short&"; }
        if(medHairChecked){ api_url = api_url + "coat=medium&"; }
        if(longChecked){ api_url = api_url + "coat=long&"; }
        if(wireChecked){ api_url = api_url + "coat=wire&"; }
        if(hairlessChecked){ api_url = api_url + "coat=hairless&"; }
        if(curlyChecked){ api_url = api_url + "coat=curly&"; }
        if(yesSpNdChecked){ api_url = api_url + "special_needs=1&"; }
     //   if(noSpNdChecked){ api_url = api_url + "special_needs&"; }
        if(declawed){ api_url = api_url + "declawed=1&"; }
        if(houseTrained){ api_url = api_url + "house_trained=1&"; }
        if(gwChildren){ api_url = api_url + "good_with_children=1&"; }
        if(gwCats){ api_url = api_url + "good_with_cats=1&"; }
        if(gwDogs){ api_url = api_url + "good_with_dogs=1&"; }


        client.addHeader("Accept", "application/json");
        client.addHeader("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJsajg2ekZPU0k3YTB3QVlEZUR0WVBLcUxodVdEcFE5UUN3bUxlejB6d0FpdmFTSVUzcyIsImp0aSI6ImE3MmJlMTljM2ZkNGE1OTc1YWEzYTMzOWM1OWY2NTc3YWJlNTExMmJiNDQ2MWIxNjI1YTM0ODIyYjczZWU3NjE5ODhmYjk1OGJkNDNlMjE4IiwiaWF0IjoxNjE4MzU4MjU5LCJuYmYiOjE2MTgzNTgyNTksImV4cCI6MTYxODM2MTg1OSwic3ViIjoiIiwic2NvcGVzIjpbXX0.MOKcHEWMZqzcwG-9KcJg7mz0i7XE9k8CJ0qKzXiXS4ilwx3z3AHZ4pP5uvwsMeq0LO3xHh5bImA8T9sx04X6kEpDUh1Zs9KU3j8R18F28IEmtDmI56ldqGaRcDVZoNGbD35hYcVGM1d3zqQmBaGKT87VZ8Xzm3ctrGK69n9rdb6a8vVm0xcUU1pA8Tjk7D8HsU5zFbADIEJjtu-9P-zvm77eXuASVmR5mf8JlsOFvNg_nzZqQdyRAGHQOLjDz3QRqoKNCzwBt4KYyg-P6w3Ol6Ky0CqueBU2dNDk2rexMe9hakRfb6OkaTYaIR4Ie3LVtfAaV_NClNlETpcx-cDSWg");
        Log.d("im not sure", api_url);
        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));
                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);

                    list_id = new ArrayList<String>();
                    list_type = new ArrayList<String>();
                    list_name = new ArrayList<String>();
                    list_age = new ArrayList<String>();
                    list_gender = new ArrayList<String>();
                    list_photos = new ArrayList<String>();

                    JSONArray animal = json.getJSONArray("animals");

                    for(int i = 0; i < animal.length(); i++){
                        JSONObject temp = animal.getJSONObject(i);

                        String id = temp.getString("id");
                        list_id.add(id);

                        String type = temp.getString("type");
                        list_type.add(type);

                        String name = temp.getString("name");
                        list_name.add(name);

                        String age = temp.getString("age");
                        list_age.add(age);

                        String gender = temp.getString("gender");
                        list_gender.add(gender);

                       // String photos = temp.getString("photos");
                        JSONArray ph_arr = temp.getJSONArray("photos");
                        if(ph_arr.length() > 0) {
                            JSONObject te = ph_arr.getJSONObject(0);
                            String testing = te.getString("small");
                            System.out.println("this is testing " + testing);
                            list_photos.add(testing);
                        }else{
                            list_photos.add("https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/51202556/2/?bust=1618338275&width=100");
                        }

                        System.out.println("this is arrrrrrrr " + ph_arr);

                    }


                    intent.putStringArrayListExtra("id", (ArrayList<String>) list_id); // this used for later trust me LOL, but not displayed in next page
                    intent.putStringArrayListExtra("type", (ArrayList<String>) list_type);
                    intent.putStringArrayListExtra("name", (ArrayList<String>) list_name);
                    intent.putStringArrayListExtra("age", (ArrayList<String>) list_age);
                    intent.putStringArrayListExtra("gender", (ArrayList<String>) list_gender);
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

}
