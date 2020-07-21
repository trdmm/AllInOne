package com.wdnyjx.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.EventBus;
import com.wdnyjx.Entity.RandJoke;
import com.wdnyjx.HttpTest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * 处理需要发送的请求
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx.Utils.HttpUtil
 * @author:OverLord
 * @Since:2020/6/15 11:09
 * @Version:v0.0.1
 */
@Service
@Slf4j
public class HttpServiceTest {
    @Autowired
    OkHttpClient okHttpClient;
    @Autowired
    EventBus eventBus;
    /**
     * 向赣机惠农 农信通传数据
     *
     * @param url      请求地址
     * @param dataList 请求传送的数据
     */
    public <T> String postToNXT(String url, List<T> dataList) throws JsonProcessingException {
        if (CollectionUtils.isEmpty(dataList)) {
            return "数据长度不能为空";
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dataList);
        RequestBody body = new FormBody.Builder()
                .add("data", json)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            //TODO 异步消费发送
            Response response = okHttpClient.newCall(request).execute();
            String result = response.body().string();
            return result;
        } catch (IOException e) {
            log.error(e.getMessage());
            return "Post Error!\n" + e.getMessage();
        }
    }

    public String postJoke(String url) {
        eventBus.register(new HttpTest());
        Request request = new Request.Builder()
                .url(url)
                .get().build();
        try {
            //Response response = okHttpClient.newCall(request).execute();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    eventBus.post(response.body().string());
                }
            });
            //String result = response.body().string();
            //return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "result";
    }
}
