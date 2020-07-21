package com.wdnyjx.Service;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * File ...
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx.Service
 * @author:OverLord
 * @Since:2020/6/20 11:09
 * @Version:v0.0.1
 */
@Slf4j
public class LogAppInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String originalUrl = originalRequest.url().toString();
        RequestBody requestBody = originalRequest.body();
        Response response = chain.proceed(originalRequest);
        //==============拷贝source，防止body只能使用一次============
        ResponseBody body = response.body();
        BufferedSource source = body.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.getBuffer();
        String result = buffer.clone().readUtf8();
        //======================================================
        log.debug("请求结果:{}",result);
        //ResponseBody fuck = ResponseBody.create("Fuck", MediaType.get("text/plain"));
        ResponseBody fuck = ResponseBody.create("fuck",MediaType.parse("text/plain"));

        response = response.newBuilder().body(fuck).build();
        return response;
    }
}
