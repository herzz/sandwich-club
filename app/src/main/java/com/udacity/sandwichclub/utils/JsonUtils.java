package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        Sandwich mSandwich;
        String mainName = "";
        List<String> alsoKnownAs = null;
        String description;
        String placeOfOrigin;
        String image;
        List<String> ingredients;


        try {
            if (json.length() > 0) {
                JSONObject sandwichJson = new JSONObject(json);
                if (sandwichJson.has(Constants.NAME)) {
                    JSONObject name = sandwichJson.optJSONObject(Constants.NAME);
                    mainName = name.optString(Constants.MAIN_NAME);
                    JSONArray alsoKnownAsArray = name.optJSONArray(Constants.ALSO_KNOW_AS);
                    alsoKnownAs = new ArrayList<>();

                    for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                        alsoKnownAs.add(alsoKnownAsArray.getString(i));
                    }
                }
                description = sandwichJson.optString(Constants.DESCRIPTION);
                image = sandwichJson.optString(Constants.IMAGE);
                placeOfOrigin = sandwichJson.optString(Constants.PLACE_OF_ORGIN);
                JSONArray ingredientsArray = sandwichJson.getJSONArray(Constants.INGREDIENTS);
                ingredients = new ArrayList<>();

                for (int i = 0; i < ingredientsArray.length(); i++) {
                    ingredients.add(ingredientsArray.getString(i));
                }

                mSandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
                return mSandwich;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
