package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sandwichDetails = new JSONObject(json);

            JSONObject name = sandwichDetails.getJSONObject("name");

            String mainName = name.getString("mainName");

            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            List<String> arrayAlsoKnownAs = jsonArrayToList(alsoKnownAs);

            String placeOfOrigin = sandwichDetails.getString("placeOfOrigin");

            String description = sandwichDetails.getString("description");

            String image = sandwichDetails.getString("image");

            JSONArray ingredients = sandwichDetails.getJSONArray("ingredients");
            List<String> arrayIngredients = jsonArrayToList(ingredients);

            Sandwich sandwich = new Sandwich(mainName, arrayAlsoKnownAs, placeOfOrigin, description, image, arrayIngredients);

            return sandwich;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> jsonArrayToList(JSONArray jsonArray){
        List<String> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++){
            try {
                list.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
