package com.example.pawfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private Button button_share, button_backAct4;

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

        button_share = findViewById(R.id.button_share);
        button_backAct4 = findViewById(R.id.button_backAct4);

        button_backAct4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchFourthAct();
            }
        });

        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchFifthAct();
            }
        });


        Intent intent = getIntent();
        String id = intent.getStringExtra("pet");

        String api_url = "https://api.petfinder.com/v2/animals/" + id;

        client.addHeader("Accept", "application/json");
        client.addHeader("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJsajg2ekZPU0k3YTB3QVlEZUR0WVBLcUxodVdEcFE5UUN3bUxlejB6d0FpdmFTSVUzcyIsImp0aSI6ImJjM2E2M2MwMzM0Y2Y4YjkyMWM5NmExYTM5MGVkYTY3MmY0ZDAzMTdhZmNhZmY3MjcxZWY2OWFjNzNhN2E5ZGE4MmYyMjhmMTczNWEwY2VhIiwiaWF0IjoxNjE5NDc4NDIzLCJuYmYiOjE2MTk0Nzg0MjMsImV4cCI6MTYxOTQ4MjAyMywic3ViIjoiIiwic2NvcGVzIjpbXX0.RNHLZUk2rz0cwo4epTVeIjWEMsOgGgs5G1fqe3hBPOF7dQwHLPC4scCZ53TjUbzy0IcMS0jitws4qy-7-VceUb_7PnfU2Ljwl_t3sNNdmDfbYZE07cSIOE-7MapoaMmZFVW4gDRFz8UmAUm5KKIoaXI7BpcRe06jFFBnrQ9W9dqW26hCWrryQFYDwsbIqLDEvSeWo7Q31H7gab-JFt8DswEn7t7agi1z3sTJ7AvEjaOmZV1SjCVE1HSv6GP5zDZTBI_ZjJpgP5WLnMK84YEnDoNJnus-UeI9IR_Lz78cQjchVQugphosSuwQYU3vw6Yvk2I0v2-QF-T2c2vRC9Ivbw");
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


}
