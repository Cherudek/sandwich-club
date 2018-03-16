package com.udacity.sandwichclub.utils;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();
    public static final String JSON_BASE_OBJECT_KEY = "name";
    public static final String JSON_MAIN_NAME_KEY = "mainName";
    public static final String JSON_AKA_KEY = "alsoKnownAs";
    public static final String JSON_PLACE_OF_ORIGIN_KEY = "placeOfOrigin";
    public static final String JSON_DESCRIPTION_KEY = "description";
    public static final String JSON_IMAGE_KEY = "image";
    public static final String JSON_INGREDIENTS_KEY = "ingredients";






    /**
         * Return a list of {@link Sandwich} objects that has been built up from
         * parsing the given JSON response.
         */
        public static Sandwich parseSandwichJson(String json) {

            // If the JSON string is empty or null, then return early.
            if (TextUtils.isEmpty(json)) {
                return null;
            }

            // Create two empty ArrayList that we can start adding sandwiches to Aka names and Ingredients list
            Sandwich sandwich = null;
            List<String> akaList = new ArrayList<>();
            List<String> ingredientList = new ArrayList<>();

            // Try to parse the JSON response string. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
            try {

                // Create a JSONObject from the JSON response string
                JSONObject baseJsonResponse = new JSONObject(json);

                //Extract the main JSON object called name
                JSONObject name = baseJsonResponse.getJSONObject(JSON_BASE_OBJECT_KEY);

                String mainName = name.getString(JSON_MAIN_NAME_KEY);
                Log.d(LOG_TAG, "The Main Name is: " + mainName);

                JSONArray alsoKnownAsArray = name.getJSONArray(JSON_AKA_KEY);
                Log.d(LOG_TAG, "The Also Known as Array is: " + alsoKnownAsArray);

                // For each item in the alsoKnownAsArray create a String
                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    String alsoKnownAs = alsoKnownAsArray.getString(i);
                    Log.d(LOG_TAG, "The Also Known as  Name are / is: " + alsoKnownAs);
                    akaList.add(alsoKnownAs);
                }

                String placeOfOrigin = baseJsonResponse.getString(JSON_PLACE_OF_ORIGIN_KEY);
                Log.d(LOG_TAG, "The Place of Origin is: " + placeOfOrigin);

                String description = baseJsonResponse.getString(JSON_DESCRIPTION_KEY);
                Log.d(LOG_TAG, "The Sandwich description is: " + description);

                String image = baseJsonResponse.getString(JSON_IMAGE_KEY);
                Log.d(LOG_TAG, "The Sandwich image url is: " + image);

                JSONArray ingredientsArray = baseJsonResponse.getJSONArray(JSON_INGREDIENTS_KEY);
                Log.d(LOG_TAG, "The ingredients Array is: " + ingredientsArray);

                // For each item in the ingredients Array create a String
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    String ingredient = ingredientsArray.getString(i);
                    ingredientList.add(ingredient);
                    Log.d(LOG_TAG, "The Ingredients are: " + ingredient);
                }

                //return a new sandwich object
                sandwich = new Sandwich(mainName, akaList, placeOfOrigin, description, image, ingredientList);

            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("parseSandwichJson", "Problem parsing the JSON results", e);
            }

            // Return a sandwich object to the detail activity
            return  sandwich;
        }
    }

