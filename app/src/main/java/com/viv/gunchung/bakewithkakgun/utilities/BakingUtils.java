package com.viv.gunchung.bakewithkakgun.utilities;

import android.text.TextUtils;
import android.webkit.URLUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.viv.gunchung.bakewithkakgun.models.Recipe;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gunawan on 17/09/17.
 */

public final class BakingUtils {

    private static final String API_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static final String SELECTED_RECIPE_KEY = "selected_recipe";
    public static final String SELECTED_STEP_IDX = "selected_step_index";

    public static Response getResponseFromServer() throws IOException {
        String url = API_URL;
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }

    public static List<Recipe> parseToRecipeList(Response response) {

        List<Recipe> parsedRecipeData;

        try {
            String body = response.body().string();
            parsedRecipeData = JSON.parseObject(body, new TypeReference<List<Recipe>>() {});

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return parsedRecipeData;
    }

    public static boolean isValidUrl(String url) {
        return (!TextUtils.isEmpty(url) && URLUtil.isValidUrl(url));
    }
}
