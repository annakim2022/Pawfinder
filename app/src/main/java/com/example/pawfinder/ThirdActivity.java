package com.example.pawfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {


    private ImageView imageView_pet;
    private TextView textView_typeAct3;
    private TextView textView_nameAct3;
    private TextView textView_ageAct3;
    private TextView textView_genderAct3;
    private Button button_next;
    private Button button_moreInfo;
    private int counter = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        imageView_pet = findViewById(R.id.imageView_pet);
        textView_typeAct3 = findViewById(R.id.textView_typeAct3);
        textView_nameAct3 = findViewById(R.id.textView_nameAct3);
        textView_ageAct3 = findViewById(R.id.textView_ageAct3);
        textView_genderAct3 = findViewById(R.id.textView_genderAct3);
        button_next = findViewById(R.id.button_next);
        button_moreInfo = findViewById(R.id.button_moreInfo);

        Intent intent = getIntent();

        ArrayList<String> id = intent.getStringArrayListExtra("id");
        ArrayList<String> type = intent.getStringArrayListExtra("type");
        ArrayList<String> name = intent.getStringArrayListExtra("name");
        ArrayList<String> age = intent.getStringArrayListExtra("age");
        ArrayList<String> gender = intent.getStringArrayListExtra("gender");
        ArrayList<String> photos = intent.getStringArrayListExtra("photo");

        textView_typeAct3.setText("Type: " + type.get(0));
        textView_nameAct3.setText("Name: " + name.get(0));
        textView_ageAct3.setText("Age: " + age.get(0));
        textView_genderAct3.setText("Gender: " + gender.get(0));
        Picasso.get().load(photos.get(0)).into(imageView_pet);

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter ++;
                nextPet(type, name, age, gender, photos, counter);
            }
        });

        button_moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(counter);
                launchNextActivity(counter, id);
            }
        });
    }


    public void nextPet(ArrayList<String> type, ArrayList<String> name, ArrayList<String> age, ArrayList<String> gender, ArrayList<String> photos, int counter){
        textView_typeAct3.setText("Type: " + type.get(counter));
        textView_nameAct3.setText("Name: " + name.get(counter));
        textView_ageAct3.setText("Age: " + age.get(counter));
        textView_genderAct3.setText("Gender: " + gender.get(counter));
        Picasso.get().load(photos.get(counter)).into(imageView_pet);
    }

    public void launchNextActivity(int counter, ArrayList<String> id){
        String pet = id.get(counter);
        Intent intent = new Intent(this, FourthActivity.class);
        intent.putExtra("pet", pet);
        System.out.println("THIS IS THE PET ID " + pet);
        startActivity(intent);
    }


}
