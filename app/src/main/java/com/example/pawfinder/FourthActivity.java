package com.example.pawfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class FourthActivity extends AppCompatActivity {

    private TextView textView_nameAct4, textView_typeAct4, textView_ageAct4, textView_genderAct4, textView_breedAct4, textView_sizeAct4, textView_colorAct4, textView_coatAct4,
                        textView_declawedAct4, textView_houseTrainedAct4, textView_specialNeedsAct4, textView_gwChildrenAct4, textView_gwCatsAct4, textView_gwDogsAct4;
    private ImageView imaageView_act4;

    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

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

        Intent intent = getIntent();
        String id = intent.getStringExtra("pet");

        String api_url = "https://api.petfinder.com/v2/animals/" + id;

        client.addHeader("Accept", "application/json");
        client.addHeader("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJsajg2ekZPU0k3YTB3QVlEZUR0WVBLcUxodVdEcFE5UUN3bUxlejB6d0FpdmFTSVUzcyIsImp0aSI6ImE3MmJlMTljM2ZkNGE1OTc1YWEzYTMzOWM1OWY2NTc3YWJlNTExMmJiNDQ2MWIxNjI1YTM0ODIyYjczZWU3NjE5ODhmYjk1OGJkNDNlMjE4IiwiaWF0IjoxNjE4MzU4MjU5LCJuYmYiOjE2MTgzNTgyNTksImV4cCI6MTYxODM2MTg1OSwic3ViIjoiIiwic2NvcGVzIjpbXX0.MOKcHEWMZqzcwG-9KcJg7mz0i7XE9k8CJ0qKzXiXS4ilwx3z3AHZ4pP5uvwsMeq0LO3xHh5bImA8T9sx04X6kEpDUh1Zs9KU3j8R18F28IEmtDmI56ldqGaRcDVZoNGbD35hYcVGM1d3zqQmBaGKT87VZ8Xzm3ctrGK69n9rdb6a8vVm0xcUU1pA8Tjk7D8HsU5zFbADIEJjtu-9P-zvm77eXuASVmR5mf8JlsOFvNg_nzZqQdyRAGHQOLjDz3QRqoKNCzwBt4KYyg-P6w3Ol6Ky0CqueBU2dNDk2rexMe9hakRfb6OkaTYaIR4Ie3LVtfAaV_NClNlETpcx-cDSWg");
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

                    String type = animal.getString("type");
                    textView_typeAct4.setText("Type: " + type);

                    String age = animal.getString("age");
                    textView_ageAct4.setText("Age: " + age);

                    String gender = animal.getString("gender");
                    textView_genderAct4.setText("Gender: " + gender);

                    JSONObject breed_obj = animal.getJSONObject("breeds");
                    String breed = breed_obj.getString("primary");
                    textView_breedAct4.setText("Primary Breed: " + breed);

                    String size = animal.getString("size");
                    textView_sizeAct4.setText("Size: " + size);

                    JSONObject color_obj =  animal.getJSONObject("colors");
                    String color = color_obj.getString("primary");
                    textView_colorAct4.setText("Primary Color: " + color);

                    String coat = animal.getString("coat");
                    textView_coatAct4.setText("Coat: " + coat);

                    JSONObject attributes = animal.getJSONObject("attributes");
                    String declawed = attributes.getString("declawed");
                    if(declawed == "null"){
                        textView_declawedAct4.setText("Declawed: No");
                    }else{
                        textView_declawedAct4.setText("Declawed: Yes");
                    }
                    System.out.println("This is declawed in act 4 " + declawed);

                    String house_trained = attributes.getString("house_trained");
                    if(house_trained == "false"){
                        textView_houseTrainedAct4.setText("House Trained: No");
                    }else{
                        textView_houseTrainedAct4.setText("House Trained: Yes");
                    }
                    System.out.println(house_trained);

                    String special_needs = attributes.getString("special_needs");
                    if(special_needs == "true"){
                        textView_specialNeedsAct4.setText("Special Needs: Yes");
                    }else{
                        textView_specialNeedsAct4.setText("Speical Needs: No");
                    }
                    System.out.println(special_needs);

                    JSONObject environment = animal.getJSONObject("environment");

                    String children = environment.getString("children");
                    if(children == "true"){
                        textView_gwChildrenAct4.setText("Good with Children: Yes");
                    }else{
                        textView_gwChildrenAct4.setText("Good with Children: No");
                    }
                    System.out.println(children);

                    String dogs = environment.getString("dogs");
                    if(dogs == "true"){
                        textView_gwDogsAct4.setText("Good with Dogs: Yes");
                    }else{
                        textView_gwDogsAct4.setText("Good with Dogs: No");
                    }
                    System.out.println(dogs);

                    String cats = environment.getString("cats");
                    if(cats == "true"){
                        textView_gwCatsAct4.setText("Good with Cats: Yes");
                    }else{
                        textView_gwCatsAct4.setText("Good with Cats: No");
                    }
                    System.out.println(cats);





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
