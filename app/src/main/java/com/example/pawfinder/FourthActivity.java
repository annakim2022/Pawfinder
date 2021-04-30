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
    private Button button_share, button_backAct4, button_contactOrg;

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
        button_contactOrg = findViewById(R.id.button_contactOrg);

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

        button_contactOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mine isn't correct but I think yours is from homework 3
                // https://api.petfinder.com/v2/organizations/nj333
                // this is from the documentation in the petfinder api
                // not sure why but it isn't working lol
            }
        });


        Intent intent = getIntent();
        String id = intent.getStringExtra("pet");

        String api_url = "https://api.petfinder.com/v2/animals/" + id;

        client.addHeader("Accept", "application/json");
        client.addHeader("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJsajg2ekZPU0k3YTB3QVlEZUR0WVBLcUxodVdEcFE5UUN3bUxlejB6d0FpdmFTSVUzcyIsImp0aSI6ImE2NjNlMzFiMzUwZWZhYjA1OTM5OWM0NTFmNzk3MDg2MDg1YzQ5ZjU1MTk3MmU3ODA0Njg1ZjcwZDBkYzA5ODBkN2YyOGM1ZmNlYzllNzYzIiwiaWF0IjoxNjE5ODEzMjE4LCJuYmYiOjE2MTk4MTMyMTgsImV4cCI6MTYxOTgxNjgxOCwic3ViIjoiIiwic2NvcGVzIjpbXX0.iVqQpm8yy5o-zhQv8M-SjjMQoi34isfYRGN1jbj5ZnjSYhQGtGqCWxWxm25CUgTSXeDqlTb8YwKqkOGgWCqnkhfZdPrveJszg8_WTe0jIsYyG-zy0uyUex5wvf4t_x6to6X1I2Fmmce5Ebw52LMofn2tUICzA9Ef-6ynf0bNqWVKVOHzcnPIRIrsnPZWNZP1ttufDXXWWy9AsJC6jkNFaTWX1XcDhLK_BLtPq4uAKCseAgSwFbT8EykPrLmpnO1lqOgCPCb_X2JxFFIf5Vc58Zi2QJ07qC_P--R8SlSAQgI8RlosItNduPK5qB0AsZZ2FOY-2kmQoCkgx9i_8m6ESA");
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
