package com.wdnyjx.Utils.HttpUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.concurrent.TimeUnit;

/**
 * 网络请求
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx.Utils
 * @author:OverLord
 * @Since:2020/6/12 13:26
 * @Version:v0.0.1
 */
@Configuration
@Slf4j
public class HttpClients {
    @Bean
    @Qualifier("okhttp")
    @Lazy
    public OkHttpClient getHttpClient(){
//        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    /**
     * 需要spring web包
     * @return OkHttp3实现的RestTemplate
     */
/*    @Bean
    @Qualifier("OkHttpTemplate")
    public RestTemplate OkHttpTemplate(){
        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory(getHttpClient()));
        return restTemplate;
    }*/
}
