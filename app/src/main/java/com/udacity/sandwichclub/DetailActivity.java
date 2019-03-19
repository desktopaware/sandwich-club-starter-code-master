package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView alsoKnownAsTv;
    private TextView alsoKnownAs;
    private TextView placeOfOriginTv;
    private TextView placeOfOrigin;
    private TextView descriptionTv;
    private TextView ingredientTv;
    private ImageView sandwichIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        alsoKnownAs = findViewById(R.id.also_known_as);
        placeOfOriginTv = findViewById(R.id.origin_tv);
        placeOfOrigin = findViewById(R.id.place_of_origin);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientTv = findViewById(R.id.ingredients_tv);
        sandwichIv = findViewById(R.id.image_iv);

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

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwichIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        if(!sandwich.getAlsoKnownAs().isEmpty()){
            int size = 1;
            String alsoKnownAsText = "";
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++){
                if(size == sandwich.getAlsoKnownAs().size()){
                    alsoKnownAsText += (sandwich.getAlsoKnownAs().get(i) + ".");
                }else {
                    alsoKnownAsText += (sandwich.getAlsoKnownAs().get(i) + ", ");
                }
                size++;
            }
            alsoKnownAsTv.setText(alsoKnownAsText);
        }else {
            alsoKnownAsTv.setVisibility(View.GONE);
            alsoKnownAs.setVisibility(View.GONE);
        }

        if(!sandwich.getPlaceOfOrigin().isEmpty()){
            placeOfOriginTv.setText(sandwich.getPlaceOfOrigin() + ".");
        }else {
            placeOfOriginTv.setVisibility(View.GONE);
            placeOfOrigin.setVisibility(View.GONE);
        }

        descriptionTv.setText(sandwich.getDescription());

        if(!sandwich.getIngredients().isEmpty()){
            String ingredientsAsText = "";
            for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                ingredientsAsText += ( (i+1) + "- " +sandwich.getIngredients().get(i) + ". \n\n");
            }
            ingredientTv.setText(ingredientsAsText);
        }

    }
}
