package com.wdnyjx.Utils;

import com.google.common.base.Stopwatch;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.wdnyjx.Utils.HttpUtil.HttpService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * File ...
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx.Utils
 * @author:OverLord
 * @Since:2020/6/18 19:36
 * @Version:v0.0.1
 */
@Configuration
public class EventBusUtil {
    @Bean
    @Lazy
    public EventBus getEventBus(){
        return new EventBus();
    }
}

