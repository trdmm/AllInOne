package com.wdnyjx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wdnyjx.Entity.DB.FrameAndType;
import com.wdnyjx.Entity.DB.TmlAndId;
import com.wdnyjx.Entity.RandJoke;
import com.wdnyjx.Entity.ResultItem;
import com.wdnyjx.Entity.gjhn.MachineryInfo;
import com.wdnyjx.Entity.gjhn.WorkArea;
import com.wdnyjx.Entity.gjhn.WorkRecordInfo;
import com.wdnyjx.Service.IService;
import com.wdnyjx.Utils.ConstantUtil;
import okhttp3.*;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * File ...
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx
 * @author:OverLord
 * @Since:2020/6/13 10:00
 * @Version:v0.0.1
 */
@SpringBootTest
public class SampleNXTTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleNXTTask.class);
    @Autowired
    private IService serviceImp;
    @Autowired
    @Qualifier("okhttp")
    OkHttpClient okHttpClient;

    @Test
    public void test01() throws IOException {
        RequestBody body = new FormBody.Builder()
                .add("key", "0806a44689b45febe55a2ba54037610a")
                .build();
        Request request = new Request.Builder()
                .url("http://v.juhe.cn/joke/randJoke.php")
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        ObjectMapper mapper = new ObjectMapper();
        RandJoke randJoke = mapper.readValue(response.body().string(), RandJoke.class);
        for (ResultItem resultItem : randJoke.getResult()) {
            LOGGER.debug(resultItem.getContent());
        }
    }

    @Test
    public void test02() {
        List<MachineryInfo> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MachineryInfo machineryInfo = new EasyRandom().nextObject(MachineryInfo.class);
            list.add(machineryInfo);
        }
        LOGGER.debug(list + "");
        LOGGER.debug(list.toString());
    }

    /**
     * 时间
     */
    @Test
    public void test03() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Instant now = Instant.now();
        OffsetDateTime nowDateTime = now.atOffset(ZoneOffset.ofHours(8));
        Instant yesterday = now.minus(1, ChronoUnit.DAYS);
        OffsetDateTime yesterdayDateTime = yesterday.atOffset(ZoneOffset.ofHours(8));
        LOGGER.debug(nowDateTime.toString());
        LocalDateTime localDateTime = yesterdayDateTime.toLocalDate().atTime(0, 0, 0);
        LOGGER.debug(yesterdayDateTime.toString());
        LOGGER.debug(localDateTime.toString());
        System.out.println("------");

        Calendar cal = Calendar.getInstance();
        cal.set(2020, 00, 01);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        System.out.println("Today's date is " + dateFormat.format(cal.getTime()));

        cal.add(Calendar.DATE, -1);
        System.out.println("Yesterday's date was " + dateFormat.format(cal.getTime()));

        /**
         * 	// 获得某天最大时间 2020-02-19 23:59:59
         *     public static Date getEndOfDay(Date date) {
         *         LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());;
         *         LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
         *         return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
         *     }
         ***************************************************************************************************************
         *     // 获得某天最小时间 2020-02-17 00:00:00
         *     public static Date getStartOfDay(Date date) {
         *         LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
         *         LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
         *         return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
         *     }
         */
    }

    @Test
    public <T> void test04() {
        Cache<String, List<T>> cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1, TimeUnit.DAYS)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .recordStats()
                .build();

        //LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(Instant.now().minus(1,ChronoUnit.DAYS).toEpochMilli()), ZoneId.systemDefault());;
        Instant now = Instant.now();
        Instant yesterday = now.minus(1, ChronoUnit.DAYS);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(yesterday, ZoneId.systemDefault());
        LocalDateTime stime = localDateTime.toLocalDate().atTime(23, 0, 0);
        LocalDateTime etime = localDateTime.toLocalDate().atTime(23, 59, 59, 9999);
/*        LocalDateTime stime = localDateTime.with(LocalTime.MIN);
        LocalDateTime etime = localDateTime.with(LocalTime.MAX);*/
        LOGGER.debug(stime.toString());
        LOGGER.debug(etime.toString());
        //List<WorkAreas> workRecords = serviceImp.getWorkRecords(Timestamp.valueOf(stime),Timestamp.valueOf(etime));
        //LOGGER.debug(workRecords.toString());
    }

/*    @Test
    public void test05() {
        Instant now = Instant.now();
        Instant yesterday = now.minus(1, ChronoUnit.DAYS);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(yesterday, ZoneId.systemDefault());
        LocalDateTime stime = localDateTime.toLocalDate().atTime(23, 0, 0);
        LocalDateTime etime = localDateTime.toLocalDate().atTime(23, 59, 59, 9999);
        Stopwatch stopwatch = Stopwatch.createStarted();
//        List<TmlAndId> tmls = serviceImp.listTmls();
//        long mills = stopwatch.elapsed(TimeUnit.NANOSECONDS);
//        LOGGER.debug("终端查询耗时:" + mills);
//        stopwatch = stopwatch.reset().start();
//        List<FrameAndType> machines = serviceImp.listMachines();
//        mills = stopwatch.elapsed(TimeUnit.MILLISECONDS);
//        LOGGER.debug("车辆查询耗时:" + mills);
//        LOGGER.debug("长度:"+machines.size());
//        Map<String, String> map = new HashMap<>();
//        tmls.forEach(tml -> map.put(tml.getID(), tml.getTERMINAL_CODE()));
//        stopwatch = stopwatch.reset().start();
//        List<WorkArea> workRecords = serviceImp.getWorkRecords(Timestamp.valueOf(stime), Timestamp.valueOf(etime));
//        mills = stopwatch.elapsed(TimeUnit.NANOSECONDS);
//        LOGGER.debug("作业面积查询耗时:" + mills);
        ArrayList<WorkRecordInfo> workRecordInfosFor = new ArrayList<>();
        ArrayList<WorkRecordInfo> workRecordInfosForEach = new ArrayList<>();
        ArrayList<WorkRecordInfo> workRecordInfosIterator = new ArrayList<>();
        ArrayList<WorkRecordInfo> workRecordInfosIteratorForEach = new ArrayList<>();
        //for
        int size = workRecords.size();
        WorkArea workArea = null;
        stopwatch = stopwatch.reset().start();
        for (int i = 0; i < size; i++) {
            WorkRecordInfo info = new WorkRecordInfo();
            workArea = workRecords.get(i);
            info.setIotNumber(map.get(workArea.getTmlId()));
            info.setRegionCode(workArea.getProvince());
            info.setDicName("深松作业");
            info.setMfrsArea(workArea.getGrossArea());
            info.setStartTime(workArea.getJobStartTime());
            info.setEndTime(workArea.getJobEndTime());
            workRecordInfosFor.add(info);
        }
        mills = stopwatch.elapsed(TimeUnit.NANOSECONDS);
        LOGGER.debug("for耗时:" + mills);
        //foreach
        stopwatch = stopwatch.reset().start();
        workRecords.forEach(workArea1 -> {
            WorkRecordInfo info = new WorkRecordInfo();
            info.setIotNumber(map.get(workArea1.getTmlId()));
            info.setRegionCode(workArea1.getProvince());
            info.setDicName("深松作业");
            info.setMfrsArea(workArea1.getGrossArea());
            info.setStartTime(workArea1.getJobStartTime());
            info.setEndTime(workArea1.getJobEndTime());
            workRecordInfosForEach.add(info);
        });
        mills = stopwatch.elapsed(TimeUnit.NANOSECONDS);
        LOGGER.debug("foreach耗时:" + mills);
        //迭代器
        Iterator<WorkArea> iterator = workRecords.iterator();
        stopwatch = stopwatch.reset().start();
        while (iterator.hasNext()) {
            workArea = iterator.next();
            WorkRecordInfo info = new WorkRecordInfo();
            info.setIotNumber(map.get(workArea.getTmlId()));
            info.setRegionCode(workArea.getProvince());
            info.setDicName("深松作业");
            info.setMfrsArea(workArea.getGrossArea());
            info.setStartTime(workArea.getJobStartTime());
            info.setEndTime(workArea.getJobEndTime());
            workRecordInfosIterator.add(info);
        }
        mills = stopwatch.elapsed(TimeUnit.NANOSECONDS);
        LOGGER.debug("迭代器耗时:" + mills);
        //迭代器foreach
        stopwatch = stopwatch.reset().start();
        iterator = workRecords.iterator();
        WorkRecordInfo info = new WorkRecordInfo();
        ;
        iterator.forEachRemaining(workArea1 -> {
            info.setIotNumber(map.get(workArea1.getTmlId()));
            info.setRegionCode(workArea1.getProvince());
            info.setDicName("深松作业");
            info.setMfrsArea(workArea1.getGrossArea());
            info.setStartTime(workArea1.getJobStartTime());
            info.setEndTime(workArea1.getJobEndTime());
            try {
                workRecordInfosIteratorForEach.add((WorkRecordInfo) info.clone());

            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });
        mills = stopwatch.elapsed(TimeUnit.NANOSECONDS);
        LOGGER.debug("迭代器foreach耗时:" + mills);
    }*/

    @Test
    public void test06(){
        String result = "共1条数据，1条数据失败";
        String[] strings = result.split("，");
        String num = strings[1].substring(0, 1);
        if (!num.equals("0")){
            LOGGER.debug("失败");
        }
    }

    @Test
    public void test07(){
        String machineryInfoUrl = ConstantUtil.MachineryInfoUrl;
        String workRecordInfoUrl = ConstantUtil.WorkRecordInfoUrl;
        String reportPointUrl = ConstantUtil.ReportPointUrl;
        String reportHistoryPointUrl = ConstantUtil.ReportHistoryPointUrl;
        String parentIds = ConstantUtil.parentIds;
        LOGGER.debug(machineryInfoUrl);
        LOGGER.debug(workRecordInfoUrl);
        LOGGER.debug(reportPointUrl);
        LOGGER.debug(reportHistoryPointUrl);
        LOGGER.debug(parentIds);
    }
}
