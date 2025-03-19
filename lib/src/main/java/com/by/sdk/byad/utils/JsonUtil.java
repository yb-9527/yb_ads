package com.by.sdk.byad.utils;



import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    /**
     * 字符串转JSONObject
     */
    public static JSONObject parseObject(String jsonStr) {
        try {
            return new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串转JSONArray
     */
    public static JSONArray parseArray(String jsonStr) {
        try {
            return new JSONArray(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 安全获取String值
     */
    public static String getString(JSONObject json, String key, String defaultValue) {
        try {
            if (json != null && json.has(key) && !json.isNull(key)) {
                return json.getString(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * 安全获取int值
     */
    public static int getInt(JSONObject json, String key, int defaultValue) {
        try {
            if (json != null && json.has(key) && !json.isNull(key)) {
                return json.getInt(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * 安全获取long值
     */
    public static long getLong(JSONObject json, String key, long defaultValue) {
        try {
            if (json != null && json.has(key) && !json.isNull(key)) {
                return json.getLong(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * 安全获取double值
     */
    public static double getDouble(JSONObject json, String key, double defaultValue) {
        try {
            if (json != null && json.has(key) && !json.isNull(key)) {
                return json.getDouble(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * 安全获取boolean值
     */
    public static boolean getBoolean(JSONObject json, String key, boolean defaultValue) {
        try {
            if (json != null && json.has(key) && !json.isNull(key)) {
                return json.getBoolean(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * 安全获取JSONObject
     */
    public static JSONObject getJSONObject(JSONObject json, String key) {
        try {
            if (json != null && json.has(key) && !json.isNull(key)) {
                return json.getJSONObject(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 安全获取JSONArray
     */
    public static JSONArray getJSONArray(JSONObject json, String key) {
        try {
            if (json != null && json.has(key) && !json.isNull(key)) {
                return json.getJSONArray(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * JSONArray转List<T>
     */
    public static <T> List<T> jsonArrayToList(JSONArray jsonArray, JsonConverter<T> converter) {
        List<T> list = new ArrayList<>();
        if (jsonArray != null) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    T item = converter.convert(jsonObject);
                    if (item != null) {
                        list.add(item);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * JSON转换器接口
     */
    public interface JsonConverter<T> {
        T convert(JSONObject jsonObject) throws JSONException;
    }




    /**
     * List转JSONArray
     */
    public static JSONArray listToJsonArray(List<?> list) {
        JSONArray jsonArray = new JSONArray();
        if (list != null && !list.isEmpty()) {
            try {
                for (Object item : list) {
                    if (item instanceof Map) {
                        jsonArray.put(mapToJsonObject((Map<?, ?>) item));
                    } else if (item instanceof JSONObject) {
                        jsonArray.put(item);
                    } else if (item instanceof JSONArray) {
                        jsonArray.put(item);
                    } else {
                        // 处理基本类型和其他对象
                        jsonArray.put(item);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }

    /**
     * Map转JSONObject
     */
    private static JSONObject mapToJsonObject(Map<?, ?> map) {
        JSONObject jsonObject = new JSONObject();
        try {
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                String key = String.valueOf(entry.getKey());
                Object value = entry.getValue();

                if (value instanceof Map) {
                    jsonObject.put(key, mapToJsonObject((Map<?, ?>) value));
                } else if (value instanceof List) {
                    jsonObject.put(key, listToJsonArray((List<?>) value));
                } else if (value instanceof JSONObject) {
                    jsonObject.put(key, value);
                } else if (value instanceof JSONArray) {
                    jsonObject.put(key, value);
                } else {
                    jsonObject.put(key, value);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 对象List转JSONArray (使用对象转换器)
     */
    public static <T> JSONArray listToJsonArray(List<T> list, ObjectJsonConverter<T> converter) {
        JSONArray jsonArray = new JSONArray();
        if (list != null && !list.isEmpty()) {
            try {
                for (T item : list) {
                    JSONObject jsonObject = converter.convert(item);
                    if (jsonObject != null) {
                        jsonArray.put(jsonObject);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }

    /**
     * 对象转换器接口
     */
    public interface ObjectJsonConverter<T> {
        JSONObject convert(T object) throws JSONException;
    }

    /**
     * List转JSON字符串
     */
    public static String listToJsonString(List<?> list) {
        return listToJsonArray(list).toString();
    }

    /**
     * 对象List转JSON字符串 (使用对象转换器)
     */
    public static <T> String listToJsonString(List<T> list, ObjectJsonConverter<T> converter) {
        return listToJsonArray(list, converter).toString();
    }




    /**
     * 将任意对象转换为JSON字符串
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }

        try {
            if (obj instanceof String) {
                return "\"" + obj + "\"";
            }
            // 处理基本类型
            if (obj instanceof Number || obj instanceof Boolean) {
                return obj.toString();
            }
            // 处理数组或List
            if (obj instanceof List || obj.getClass().isArray()) {
                return objectToJsonArray(obj).toString();
            }
            // 处理Map
            if (obj instanceof Map) {
                return mapToJsonObject((Map<?, ?>) obj).toString();
            }
            // 处理普通对象
            return objectToJsonObject(obj).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }

    /**
     * 对象转JSONObject
     */
    private static JSONObject objectToJsonObject(Object obj) throws Exception {
        JSONObject json = new JSONObject();
        // 获取所有字段，包括私有字段
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);

            if (value != null) {
                if (value instanceof String) {
                    json.put(fieldName, value);
                } else if (value instanceof Number || value instanceof Boolean) {
                    json.put(fieldName, value);
                } else if (value instanceof Date) {
                    json.put(fieldName, ((Date) value).getTime());
                } else if (value instanceof List || value.getClass().isArray()) {
                    json.put(fieldName, objectToJsonArray(value));
                } else if (value instanceof Map) {
                    json.put(fieldName, mapToJsonObject((Map<?, ?>) value));
                } else {
                    // 递归处理嵌套对象
                    json.put(fieldName, objectToJsonObject(value));
                }
            }
        }
        return json;
    }

    /**
     * List或数组转JSONArray
     */
    private static JSONArray objectToJsonArray(Object obj) throws Exception {
        JSONArray jsonArray = new JSONArray();

        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            for (Object item : list) {
                addJsonArrayItem(jsonArray, item);
            }
        } else if (obj.getClass().isArray()) {
            Object[] array = (Object[]) obj;
            for (Object item : array) {
                addJsonArrayItem(jsonArray, item);
            }
        }

        return jsonArray;
    }

    /**
     * 添加项到JSONArray
     */
    private static void addJsonArrayItem(JSONArray jsonArray, Object item) throws Exception {
        if (item == null) {
            jsonArray.put(JSONObject.NULL);
        } else if (item instanceof String || item instanceof Number || item instanceof Boolean) {
            jsonArray.put(item);
        } else if (item instanceof Date) {
            jsonArray.put(((Date) item).getTime());
        } else if (item instanceof List || item.getClass().isArray()) {
            jsonArray.put(objectToJsonArray(item));
        } else if (item instanceof Map) {
            jsonArray.put(mapToJsonObject((Map<?, ?>) item));
        } else {
            jsonArray.put(objectToJsonObject(item));
        }
    }


}
