/*
 * @Abdullah Sallam
 */

package com.matager.app.common.helper.general;

import org.json.JSONObject;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

import java.util.Map;

public class JsonUtils {

    private static final JsonParser jsonParser = JsonParserFactory.getJsonParser();

    public static Map<String, Object> parseJson(String json) {
        return jsonParser.parseMap(json);
    }

    public static JSONObject parseJsonAsJSONObject(String json) {
        return new JSONObject(json);
    }
}
