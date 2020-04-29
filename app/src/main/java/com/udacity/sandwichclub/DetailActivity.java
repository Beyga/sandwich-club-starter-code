package com.udacity.sandwichclub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mAlsoKnownTv,mOriginTv, mDescriptionTv, mIngredientsTv;
    private ImageView mIngredientsIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mIngredientsIv = findViewById(R.id.image_iv);

        mAlsoKnownTv = findViewById(R.id.also_known_tv);
        mOriginTv = findViewById(R.id.origin_tv);
        mDescriptionTv = findViewById(R.id.description_tv);
        mIngredientsTv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        Picasso.with(this).setLoggingEnabled(true);
        Picasso.with(this).load(Uri.parse(sandwich.getImage())).error(R.mipmap.ic_launcher_round).into(mIngredientsIv);
        setTitle(sandwich.getMainName());
        mAlsoKnownTv.setText(displayList(sandwich.getAlsoKnownAs()));
        mOriginTv.setText(sandwich.getPlaceOfOrigin());
        mDescriptionTv.setText(sandwich.getDescription());
        mIngredientsTv.setText(displayList(sandwich.getIngredients()));
    }

    private String displayList(List<String> list){

        StringBuilder displayText = new StringBuilder();
        for(String a : list){
            displayText.append(a);
            displayText.append("\n");
        }

        return displayText.toString();

    }
}
