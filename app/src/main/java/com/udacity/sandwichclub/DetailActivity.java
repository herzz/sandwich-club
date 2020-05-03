package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    private ActivityDetailBinding binding;
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

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
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(binding.imageIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        List<String> alsoKnowAsArray = sandwich.getAlsoKnownAs();
        List<String> ingredientsArray = sandwich.getIngredients();

        populateListViewItem(alsoKnowAsArray, binding.alsoKnownTv);
        populateListViewItem(ingredientsArray, binding.ingredientsTv);

        binding.originTv.setText(sandwich.getPlaceOfOrigin());
        binding.descriptionTv.setText(sandwich.getDescription());
    }

    /**
     * Populate a TextView with a comma separated List
     * @param strings
     * @param view
     */
    private void populateListViewItem(List<String> strings, TextView view) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < strings.size(); i++) {
            stringBuilder.append(strings.get(i));
            if (strings.size() > 1 && strings.size() != i + 1) {
                stringBuilder.append(" , ");
            }
        }
        view.setText(stringBuilder.toString());
    }
}
