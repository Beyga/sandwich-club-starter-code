package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(jsonObject != null){
            try {
                JSONObject name = jsonObject.getJSONObject("name");
                String mainName = name.getString("mainName");

                JSONArray alsoKnownAsJSONArray = name.getJSONArray("alsoKnownAs");
                List<String> alsoKnownAsList = new ArrayList<>();
                for (int i = 0; i < alsoKnownAsJSONArray.length(); i++){
                    String a = alsoKnownAsJSONArray.get(i).toString();
                    alsoKnownAsList.add(a);
                }

                String placeOfOrigin = jsonObject.getString("placeOfOrigin");
                String description = jsonObject.getString("description");
                String image = jsonObject.getString("image");

                JSONArray ingredientsJSONArray = jsonObject.getJSONArray("ingredients");
                List<String> ingredientsList = new ArrayList<>();
                for (int i = 0; i < ingredientsJSONArray.length(); i++){
                    String a = ingredientsJSONArray.get(i).toString();
                    ingredientsList.add(a);
                }

                return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return null;
    }
}
