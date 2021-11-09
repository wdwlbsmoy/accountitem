package com.genuly.dou.accountitem.util;

import okhttp3.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @Auther:xueruiheng
 * @Date:2021/11/8
 * @Description:com.genuly.dou.accountitem.util
 * @version:1.0
 */
public class RequestApiGetData {

    private static String host = "https://openapi-fxg.jinritemai.com";

    private static OkHttpClient client = new OkHttpClient();

    //调用Open Api取回数据
    public static String fetch(String appKey, String method, long timestamp, String paramJson,String accessToken, String sign) throws IOException{
        String methodPath = method.replace('.','/');
        String httpurl = host + "/" + methodPath +
                "?method=" + URLEncoder.encode(method, String.valueOf(StandardCharsets.UTF_8)) +
                "&app_key=" + URLEncoder.encode(appKey, String.valueOf(StandardCharsets.UTF_8)) +
                "&access_token=" + URLEncoder.encode(accessToken, String.valueOf(StandardCharsets.UTF_8)) +
                "&timestamp=" + URLEncoder.encode(Long.toString(timestamp), String.valueOf(StandardCharsets.UTF_8)) +
                "&v=" + URLEncoder.encode("2", String.valueOf(StandardCharsets.UTF_8)) +
                "&sign=" + URLEncoder.encode(sign, String.valueOf(StandardCharsets.UTF_8)) +
                "&sign_method=" + URLEncoder.encode("hmac-sha256", String.valueOf(StandardCharsets.UTF_8));

        return post(httpurl,paramJson);
    }

    public static String get_access_token(String appKey, String timestamp, String paramJson, String sign) throws Exception{
        String geturl = host + "/token/create" +
                "?app_key=" + URLEncoder.encode(appKey, String.valueOf(StandardCharsets.UTF_8)) +
                "&method=" + URLEncoder.encode("token.create", String.valueOf(StandardCharsets.UTF_8)) +
                "&param_json=" + URLEncoder.encode(paramJson, String.valueOf(StandardCharsets.UTF_8)) +
                "&timestamp=" + URLEncoder.encode(timestamp, String.valueOf(StandardCharsets.UTF_8)) +
                "&v=" + URLEncoder.encode("2", String.valueOf(StandardCharsets.UTF_8)) +
                "&sign=" + URLEncoder.encode(sign, String.valueOf(StandardCharsets.UTF_8));
        System.out.println(geturl);
        return post(geturl,"");

    }

    public static String refresh_access_token(String appKey, long timestamp, String paramJson, String sign) throws Exception{
        String geturl = host + "/token/refresh" +
                "?app_key=" + URLEncoder.encode(appKey, String.valueOf(StandardCharsets.UTF_8)) +
                "&method=" + URLEncoder.encode("token.refresh", String.valueOf(StandardCharsets.UTF_8)) +
                "&param_json=" + URLEncoder.encode(paramJson, String.valueOf(StandardCharsets.UTF_8)) +
                "&timestamp=" + URLEncoder.encode(Long.toString(timestamp), String.valueOf(StandardCharsets.UTF_8)) +
                "&v=" + URLEncoder.encode("2", String.valueOf(StandardCharsets.UTF_8)) +
                "&sign=" + URLEncoder.encode(sign, String.valueOf(StandardCharsets.UTF_8));
        return post(geturl,"");
    }

    @SuppressWarnings("deprecation")
    public static String post(String url, String json) throws IOException{
        MediaType JSON = MediaType.get("application/json;charset=UTF-8");
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()){
            return response.body().string();
        }
    }
}
