package com.wdnyjx.Service;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * File ...
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx.Service
 * @author:OverLord
 * @Since:2020/6/20 8:25
 * @Version:v0.0.1
 */
@Slf4j
public class NetInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        //String body = request.body().toString();
        //log.debug("body = {}",body);
        String url = request.url().toString();
        log.debug("url = {}",url);
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.getBuffer();
        String result = buffer.clone().readUtf8();
        log.debug("result = {}",result);
        return response;
    }
}
