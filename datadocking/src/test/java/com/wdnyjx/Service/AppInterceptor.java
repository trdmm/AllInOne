package com.wdnyjx.Service;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * File ...
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx.Service
 * @author:OverLord
 * @Since:2020/6/20 8:16
 * @Version:v0.0.1
 */
@Slf4j
public class AppInterceptor implements Interceptor {
    String url="https://gturnquist-quoters.cfapps.io/api/random";
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Request originalRequest = chain.request();
        String originalUrl = originalRequest.url().toString();
        log.debug("原请求地址:{}",originalUrl);
        //变更请求地址
        Request request = originalRequest.newBuilder().url(url).build();

        Response response = chain.proceed(request);
        //==============拷贝source，防止body只能使用一次============
        ResponseBody body = response.body();
        BufferedSource source = body.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.getBuffer();
        String result = buffer.clone().readUtf8();
        //======================================================
        log.debug("现请求地址:{}",response.request().url());
        log.debug("请求结果:{}",result);
        long times = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        log.debug("共耗时{}ms",times);
        return response;
    }
}
