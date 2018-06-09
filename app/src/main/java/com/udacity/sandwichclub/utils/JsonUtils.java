package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * @author tasneem
 */
public class JsonUtils {

    /**
     * Parse json string to Sandwich object<br/>
     * If anything goes wrong return null.
     *
     * @param json string of all the sandwiches
     * @return @{@link Sandwich}
     */
    public static Sandwich parseSandwichJson(String json) {
        if (TextUtils.isEmpty(json)) return null;
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONObject nameObject = jsonObject.optJSONObject("name");
            if (nameObject == null) return null;
            String mainName = nameObject.optString("mainName");
            ArrayList<String> alsoKnownAs = convertJSONArrayToArray(nameObject.optJSONArray("alsoKnownAs"));
            String description = jsonObject.optString("description");
            String placeOfOrigin = jsonObject.optString("placeOfOrigin");
            String image = jsonObject.optString("image");
            ArrayList<String> ingredients = convertJSONArrayToArray(jsonObject.optJSONArray("ingredients"));
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            Log.e("JsonUtils", "parseSandwichJson: ", e);
        }
        return null;
    }

    /**
     * convert @{@link JSONArray} to @{@link ArrayList<@String>}
     * @param jsonArray of @{@link String}
     * @return @{@link ArrayList<@String>}
     */
    private static ArrayList<String> convertJSONArrayToArray(JSONArray jsonArray) {
        if (jsonArray == null) return null;
        ArrayList<String> listdata = new ArrayList<>();
        for (int i=0;i<jsonArray.length();i++){
            String val = jsonArray.optString(i);
            if (val != null) listdata.add(val);
        }
        return listdata;
    }
}
