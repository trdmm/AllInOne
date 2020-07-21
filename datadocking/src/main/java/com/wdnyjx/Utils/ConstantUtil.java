package com.wdnyjx.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * File ...
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx.Utils
 * @author:OverLord
 * @Since:2020/6/12 13:34
 * @Version:v0.0.1
 */
@Configuration
@PropertySource("classpath:jxgjhn.properties")
public class ConstantUtil {
    /**
     * 农机信息
     */
    public static String MachineryInfoUrl;

    /**
     * 作业面积
     */
    public static String WorkRecordInfoUrl;

    /**
     * 实时位置汇报
     */
    public static String ReportPointUrl;

    /**
     * 历史位置汇报
     */
    public static String ReportHistoryPointUrl;

    /**
     * 农机省份
     */
    public static String parentIds;

    //@Value("${gjhn.MachineryInfoUrl}")
    @Value("${gjhn.MachineryInfoUrl}")
    public void setMachineryInfoUrl(String machineryInfoUrl) {
        MachineryInfoUrl = machineryInfoUrl;
    }

    @Value("${gjhn.WorkRecordInfoUrl}")
    public void setWorkRecordInfoUrl(String workRecordInfoUrl) {
        WorkRecordInfoUrl = workRecordInfoUrl;
    }

    @Value("${gjhn.ReportPointUrl}")
    public void setReportPointUrl(String reportPointUrl) {
        ReportPointUrl = reportPointUrl;
    }

    @Value("${gjhn.ReportHistoryPointUrl}")
    public void setReportHistoryPointUrl(String reportHistoryPointUrl) {
        ReportHistoryPointUrl = reportHistoryPointUrl;
    }

    @Value("${gjhn.parentIds}")
    public void setParentIds(String parentIds) {
        ConstantUtil.parentIds = parentIds;
    }
}