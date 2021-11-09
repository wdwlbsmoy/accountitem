package com.genuly.dou.accountitem.util;

import com.doudian.open.gson.*;
import com.doudian.open.gson.internal.LinkedTreeMap;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther:xueruiheng
 * @Date:2021/11/8
 * @Description:com.genuly.dou.accountitem.util
 * @version:1.0
 */
public class ParamSerialize {

    //序列化参数,必须要保证三点：
    //1、保证JSON所有层级上Key的有序性
    //2、保证JSON的所有数值不带多余的小数点
    //3、保证转义逻辑与这段代码一致
    public static String marsha1(Object o){
        String raw = CustomGson.toJson(o);
        LinkedTreeMap<?,?> m = CustomGson.fromJson(raw, LinkedTreeMap.class);  //执行反序列化，把所有JSON对象转换成LinkedTreeMap
        return CustomGson.toJson(m);  //重新序列化，保证JSON所有层级上key的有序性
    }

    private static final Gson CustomGson = new GsonBuilder()
            .registerTypeAdapter(LinkedTreeMap.class,newMapSerializer())  //定制LinkedTreeMap序列化，确保所有key按字典排序
            .registerTypeAdapter(Integer.class,newNumberSerializer())    //定制数值类型序列化，确保整数输出不带小数点
            .registerTypeAdapter(Long.class,newNumberSerializer())
            .registerTypeAdapter(Double.class,newNumberSerializer())
            .disableHtmlEscaping()  //禁用HTML Escape，确保符号不转义
            .create();

    //为LinkedTreeMap定制的序列化器
    private static JsonSerializer<LinkedTreeMap<?,?>> newMapSerializer(){
        return new JsonSerializer<LinkedTreeMap<?, ?>>() {
            @Override
            public JsonElement serialize(LinkedTreeMap<?, ?> src, Type typeOfSrc, JsonSerializationContext context) {
                List<String> keys = src.keySet().stream().map(Object::toString).sorted().collect(Collectors.toList());
                JsonObject o = new JsonObject();
                for (String k:keys) {
                    o.add(k,context.serialize(src.get(k)));
                }
                return o;
            }
        };
    }

    //为Number定制化的序列化器
    private static <T extends Number> JsonSerializer<T> newNumberSerializer(){
        return new JsonSerializer<T>() {
            @Override
            public JsonElement serialize(T number, Type type, JsonSerializationContext context) {
                if (number instanceof Integer){
                    return new JsonPrimitive(number.intValue());
                }
                if (number instanceof Long){
                    return new JsonPrimitive(number.longValue());
                }
                if (number instanceof Double){
                    long longValue = number.longValue();
                    double doubleValue = number.doubleValue();
                    if (longValue == doubleValue){
                        return new JsonPrimitive(longValue);
                    }
                }
                return new JsonPrimitive(number);
            }
        };
    }
}
