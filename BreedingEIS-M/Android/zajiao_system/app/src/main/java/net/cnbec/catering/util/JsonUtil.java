package net.cnbec.catering.util;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @Describe: Json转换工具类
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class JsonUtil {

    private static Gson gson;

    private JsonUtil() {
    }

    static {
//        GsonBuilder gb = new GsonBuilder();
//        gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        gson = gb.create();
        gson = new Gson();
    }

    public static final String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static final <T> T fromJson(final String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static final <T> T fromJson(final String json, Type t) {
        return gson.fromJson(json, t);
    }

    public static final Map<String, Object> fromJson(final String json) {
        return fromJson(json, Map.class);
    }


    /**
     * 解析一个key值(方便 解析状态值)
     *
     * @param json
     * @param key
     * @return
     */
    public static String getString(String json, String key) {
        try {
            JSONObject obj = new JSONObject(json);
            return obj.optString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 解析一个key值(方便 解析状态值)
     *
     * @param jsonObj
     * @param key
     * @return
     */
    public static String getString(JSONObject jsonObj, String key) {
        try {
            return jsonObj.optString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解析一个key值(方便 解析状态值)
     *
     * @param json
     * @param key
     * @return
     */
    public static int getInt(String json, String key) {
        try {
            JSONObject obj = new JSONObject(json);
            return obj.optInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * 解析一个key值(方便 解析状态值)
     *
     * @param json
     * @param key
     * @return
     */
    public static boolean getBoolean(String json, String key) {
        try {
            JSONObject obj = new JSONObject(json);
            return obj.optBoolean(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 返回key对应的jsonObject
     * @param content
     * @param key
     * @return
     */
    public static String getJSONObject(String content, String key) {
        try{
            JSONObject obj = new JSONObject(content);
            return obj.optJSONObject(key).toString();
        }catch(Exception e){
            return null;
        }
    }
}
