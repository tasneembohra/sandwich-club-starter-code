package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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

        populateUI(position);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Populate all the ui elements
     * @param position
     */
    private void populateUI(int position) {
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView originTv = findViewById(R.id.origin_tv);
        TextView otherNameTv = findViewById(R.id.also_known_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        setTitle(sandwich.getMainName());

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        String placeOhOrigin = sandwich.getPlaceOfOrigin();
        String alsoKonwnAs = convertListToString(sandwich.getAlsoKnownAs());
        String ingredients = convertListToString(sandwich.getIngredients());
        String description = sandwich.getDescription();

        if (!TextUtils.isEmpty(placeOhOrigin)) {
            findViewById(R.id.origin_ll).setVisibility(View.VISIBLE);
            originTv.setText(sandwich.getPlaceOfOrigin());
        }

        if (!TextUtils.isEmpty(alsoKonwnAs)) {
            findViewById(R.id.also_known_ll).setVisibility(View.VISIBLE);
            otherNameTv.setText(alsoKonwnAs);
        }

        if (!TextUtils.isEmpty(ingredients)) {
            findViewById(R.id.ingredients_ll).setVisibility(View.VISIBLE);
            ingredientsTv.setText(ingredients);
        }

        if (!TextUtils.isEmpty(description)) {
            findViewById(R.id.description_title_tv).setVisibility(View.VISIBLE);
            descriptionTv.setVisibility(View.VISIBLE);
            descriptionTv.setText(sandwich.getDescription());
        }

    }

    private String convertListToString(List<String> mList) {
        if (mList == null || mList.size() == 0) return "";
        StringBuilder builder = null;
        for (String str: mList) {
            if (builder == null) {
                builder = new StringBuilder(str);
            } else {
                builder.append(", ").append(str);
            }
        }
        return builder.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
