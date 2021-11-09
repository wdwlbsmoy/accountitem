package com.genuly.dou.accountitem;

import com.doudian.open.gson.internal.LinkedTreeMap;
import com.genuly.dou.accountitem.util.GetSign;
import com.genuly.dou.accountitem.util.ParamSerialize;
import com.genuly.dou.accountitem.util.RequestApiGetData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Auther:xueruiheng
 * @Date:2021/11/9
 * @Description:com.genuly.dou.accountitem
 * @version:1.0
 */
//@SpringBootTest(classes = AccountitemApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DoudianTest {

    @Test
    public void get_access_token() throws Exception {
        //String method, long timestamp, String paramJson
        String appkey = "7025541976892114463";
        String appSecret = "8e25f920-00f1-4d43-adbe-21384fa94522";
        String method = "token.create";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
//        LinkedTreeMap<String,String> map = new LinkedTreeMap<>();
//        map.put("code","");
//        map.put("grant_type","authorization_self");
        String param_json = "{\"code\":\"\",\"grant_type\":\"authorization_self\",\"test_shop\":\"1\"}";
//        String serial_json = ParamSerialize.marsha1(map);
        String sign = GetSign.sign(appkey, appSecret, method, System.currentTimeMillis()/1000, param_json);
        System.out.println(sign);
        String token = RequestApiGetData.get_access_token(appkey, date, param_json, sign);
        System.out.println("如下是token：");
        System.out.println(token);
    }
}
