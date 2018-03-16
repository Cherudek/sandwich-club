package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.also_known_tv)
    TextView aka;
    @BindView(R.id.description_tv)
    TextView description;
    @BindView(R.id.ingredients_tv)
    TextView ingredients;
    @BindView(R.id.origin_tv)
    TextView origin;
    @BindView(R.id.image_iv)
    ImageView ingredientsIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

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
            .into(ingredientsIv);
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        List<String> akaList = sandwich.getAlsoKnownAs();
        if (akaList != null && !akaList.isEmpty()) {
            for (int i = 0; i < akaList.size(); i++) {
                String akaName = akaList.get(i);
                aka.append(akaName + "\n");
            }
        } else {
            aka.setText("N.A.");
        }

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if(!placeOfOrigin.isEmpty()){
            origin.setText(placeOfOrigin);
        } else {
            origin.setText("N.A.");
        }

        String descriptionTxt = sandwich.getDescription();
        if(!descriptionTxt.isEmpty()){
            description.setText(descriptionTxt);
        } else {
            description.setText("N.A.");
        }

        List<String> ingredientList = sandwich.getIngredients();
        for (int i = 0; i < ingredientList.size(); i++) {
            String ingredientName = ingredientList.get(i);
            ingredients.append(ingredientName + "\n" );
       }
    }

}



