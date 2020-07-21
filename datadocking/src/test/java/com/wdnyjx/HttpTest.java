package com.wdnyjx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import com.google.common.eventbus.Subscribe;
import com.wdnyjx.Service.AppInterceptor;
import com.wdnyjx.Service.HttpServiceTest;
import com.wdnyjx.Service.LogAppInterceptor;
import com.wdnyjx.Service.NetInterceptor;
import com.wdnyjx.Utils.ExecuteUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * File ...
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx
 * @author:OverLord
 * @Since:2020/6/18 18:23
 * @Version:v0.0.1
 */
@Slf4j
@SpringBootTest
public class HttpTest {
    String key = "0806a44689b45febe55a2ba54037610a";
    //String url = "http://v.juhe.cn/joke/content/text.php?key=0806a44689b45febe55a2ba54037610a&page=";
    String url = "https://api.carbonintensity.org.uk/intensity/date/2019-";
    @Autowired
    HttpServiceTest httpServiceTest;
    int index = 0;

    @Test
    public void test01() {
        ObjectMapper mapper = new ObjectMapper();
        int month, day;
        ArrayList<String> urls = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            month = (int) Math.round(Math.random() * 11 + 1);
            day = (int) Math.round(Math.random() * 30 + 1);
            String concat = url.concat(String.valueOf(month)).concat("-" + day);
            urls.add(concat);
        }

        Stopwatch stopwatch = Stopwatch.createStarted();
        ExecuteUtil.partitionRun(urls, 1, eachlist -> {
            String result = httpServiceTest.postJoke(eachlist.get(0));
            result = StringUtils.trimAllWhitespace(result);
            log.debug("{}" + result, index++);
        });
        long mills = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        log.debug("耗时{}毫秒", mills);
    }

    @Subscribe
    public void listen(String str) {
        System.out.println(str.toString());
    }

    /**
     * okhttp认证请求
     */
    @Test
    public void test02() throws IOException {
        String url = "https://bdgps.worldnyjx.com/lonher/a/cd/machine/cdMachine/getDataOne";
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LogAppInterceptor())
                .addInterceptor(new AppInterceptor())
                .addNetworkInterceptor(new NetInterceptor())
                .build();

        String credential = Credentials.basic("hmm", "123456");
        FormBody body = new FormBody.Builder().add("id", "04853d57b20a456bb0d61112eef2e4b9").build();
        Request request = new Request.Builder()
                .url(url)
                //.addHeader("Authorization", credential)
                .build();
        //.post(body).build();
        //Request request = new Request.Builder().url(url).post(body).build();
        Response response = okHttpClient.newCall(request).execute();
        log.debug(Objects.requireNonNull(response.body()).string());
    }
}
