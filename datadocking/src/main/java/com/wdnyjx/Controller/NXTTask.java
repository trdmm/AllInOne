package com.wdnyjx.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.wdnyjx.Entity.DB.FrameAndType;
import com.wdnyjx.Entity.DB.MachineAndTml;
import com.wdnyjx.Entity.DB.TmlAndId;
import com.wdnyjx.Entity.gjhn.*;
import com.wdnyjx.Service.IService;
import com.wdnyjx.Utils.ConstantUtil;
import com.wdnyjx.Utils.ExecuteUtil;
import com.wdnyjx.Utils.HttpUtil.HttpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * 江西农信通赣机惠农数据同步
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx.Controller
 * @author:OverLord
 * @Since:2020/6/13 19:22
 * @Version:v0.0.1
 */
@Component
@EnableScheduling
@Slf4j
@EnableCaching
public class NXTTask {
    @Autowired
    IService serviceImp;
    @Autowired
    HttpService httpService;
    @Autowired
    @Qualifier("guava")
    Cache cache;
    ObjectMapper mapper = new ObjectMapper();
    /**
     * 缓存
     *         Cache<String, List<T>> cache = CacheBuilder.newBuilder()
     *                 .maximumSize(100)
     *                 .expireAfterWrite(1, TimeUnit.DAYS)
     *                 .concurrencyLevel(Runtime.getRuntime().availableProcessors())
     *                 .recordStats()
     *                 .build();
     * @return
     */

    /**
     * 获取江西的车
     *
     * @return
     */
    private List<MachineryInfo> getJXList() {
        return serviceImp.listAll(ConstantUtil.parentIds);
    }

    private List<MachineAndTml> getMachineAndTmls() {
        List<MachineAndTml> machineAndTmls = serviceImp.listMachineAndTml();
        cache.put("machines", machineAndTmls);
        return machineAndTmls;
    }

    /**
     * 基础信息 0,12点一次
     */
    //@Scheduled(cron = "0 0 0,12 * * ?")
    public void MachineryInfo() {
        List<MachineryInfo> list = getJXList();

        Iterator<MachineryInfo> iterator = list.iterator();
        int size = list.size();
        log.debug("初始大小：" + size);
        if (size > 0) {
            while (iterator.hasNext()) {
                //迭代器循环并剔除拖拉机、收割机
                MachineryInfo machineryInfo = iterator.next();
                String dicName = machineryInfo.getDicName();
                if (StringUtils.isEmpty(dicName)) {
                    //农机类型为空，默认无
                    machineryInfo.setDicName("无");
                } else {
                    if (dicName.contains("收割机") || dicName.contains("拖拉机")) {
                        iterator.remove();
                        continue;
                    }
                }
                String modelName = machineryInfo.getModelName();
                if (StringUtils.isEmpty(modelName)) {
                    machineryInfo.setModelName("无");
                    machineryInfo.setMachineryModel("无");
                } else {
                    String[] remarks = modelName.split("\\|");
                    if (remarks.length == 2) {
                        machineryInfo.setModelName(StringUtils.isEmpty(remarks[0]) ? "无" : remarks[0]);
                        machineryInfo.setMachineryModel(StringUtils.isEmpty(remarks[1]) ? "无" : remarks[1]);
                    } else {
                        machineryInfo.setModelName(StringUtils.isEmpty(remarks[0]) ? "无" : remarks[0]);
                        machineryInfo.setMachineryModel("无");
                    }
                }
            }
            log.debug("去掉后大小：" + list.size());
            ExecuteUtil.partitionRun(list, 100, (eachList) -> {
                try {
                    String result = httpService.postToNXT(ConstantUtil.MachineryInfoUrl, eachList);
                    MachineryInfoResult machineryInfoResult = mapper.readValue(result, MachineryInfoResult.class);
                    if (machineryInfoResult.getCode() == 100) {
                        log.info(result);
                    } else {
                        log.error("农机信息上传错误!!!\n" + result + "\n上报数据：" + eachList.toString());
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    //作业面积 3点一次
    //@Scheduled(fixedRate = 30000)
    //@Scheduled(cron = "0 0 3 * * ?")
    public void WorkRecordInfo() throws ExecutionException {
        //获取前一天的日期
        //1.获取昨天时间
        Instant yesterday = Instant.now().minus(1, ChronoUnit.DAYS);
        ;
        //2.Instant转LocalDateTime--本地默认时区
        LocalDateTime localDateTime = LocalDateTime.ofInstant(yesterday, ZoneId.systemDefault());
//        LocalDateTime stime = localDateTime.toLocalDate().atTime(23, 0, 0);
//        LocalDateTime etime = localDateTime.toLocalDate().atTime(23, 59, 59, 9999);
        LocalDateTime stime = localDateTime.with(LocalTime.MIN);
        LocalDateTime etime = localDateTime.with(LocalTime.MAX);
        List<MachineAndTml> machineAndTmls = (List<MachineAndTml>) cache.get("machines", new Callable() {
            @Override
            public Object call() throws Exception {
                return serviceImp.listMachineAndTml();
            }
        });
        Map<String, MachineAndTml> machineAndTmlMap = new HashMap<>();
        machineAndTmls.forEach(machineAndTml -> {
            machineAndTmlMap.put(machineAndTml.getFrameNum(), machineAndTml);
        });
        //根据时间查询江西的作业数据
        List<WorkArea> workRecords = serviceImp.getWorkRecords(Timestamp.valueOf(stime), Timestamp.valueOf(etime));
        Iterator<WorkArea> iterator = workRecords.iterator();
        //存放需要上报的作业数据
        ArrayList<WorkRecordInfo> workRecordInfos = new ArrayList<>();
        WorkArea workArea;
        WorkRecordInfo info = new WorkRecordInfo();
        MachineAndTml machineAndTml;
        while (iterator.hasNext()) {
            workArea = iterator.next();
            machineAndTml = machineAndTmlMap.get(workArea.getMachineId());
            info.setIotNumber(machineAndTml.getTmlCode());
            //作业区域
            info.setRegionCode(Integer.parseInt(machineAndTml.getRegionCode()));

            //作业类型
            switch (machineAndTml.getMachineType()) {
                case "13":
                    info.setDicName("旋耕作业");
                    break;
                case "3":
                    info.setDicName("插秧作业");
                    break;
                case "1":
                    info.setDicName("深松作业");
                    break;
                default:
                    info.setDicName("收获作业");
                    break;
            }
            info.setMfrsArea(workArea.getGrossArea());
            info.setStartTime(workArea.getJobStartTime());
            info.setEndTime(workArea.getJobEndTime());
            try {
                workRecordInfos.add((WorkRecordInfo) info.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        ExecuteUtil.partitionRun(workRecordInfos, 200, eachList -> {
            try {
                String result = httpService.postToNXT(ConstantUtil.WorkRecordInfoUrl, eachList);
                WorkRecordInfoResult workRecordInfoResult = mapper.readValue(result, WorkRecordInfoResult.class);
                String msg = workRecordInfoResult.getMsg();
                //返回信息不规范，不一致
                String[] strings = msg.split("，");
                int length = strings.length;
                int errorCount = 0;
                for (int i = 0; i < length; i++) {
                    int index = strings[i].indexOf("条数据失败");
                    if (index != -1) {
                        String substring = strings[i].substring(0, index);
                        errorCount = Integer.parseInt(substring);
                    }
                }
                if (errorCount == 0) {
                    log.info(result);
                } else {
                    log.error("面积作业上传错误!!!\n" + result + "\n上报数据：" + eachList.toString());
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    //实时位置 5分钟一次
    //@Scheduled(cron = "0 0/5 * * * ?")
    public void ReportPoint() {

    }

    //历史位置 1点一次
    //@Scheduled(cron = "0 0 1 * * ?")
    public void ReportHistoryPoint() {

    }

    //@Scheduled(fixedRate = 1800000)
    public void singleMachine() throws ExecutionException {
        String frameNum = "JDN13456D";
        Calendar cal = Calendar.getInstance();
        cal.set(2020, 00, 01);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        long timeInMillis = cal.getTimeInMillis();
        Timestamp stime = new Timestamp(timeInMillis);
        cal.set(2020, 06, 13);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        timeInMillis = cal.getTimeInMillis();
        Timestamp etime = new Timestamp(timeInMillis);
        log.debug(stime.toString());
        log.debug(etime.toString());
        List<WorkArea> singleWorkRecords = serviceImp.getSingleWorkRecords(frameNum, stime, etime);

        //处理
        Iterator<WorkArea> iterator = singleWorkRecords.iterator();
        //存放需要上报的作业数据
        List<MachineAndTml> machineAndTmls = (List<MachineAndTml>) cache.get("machines", new Callable() {
            @Override
            public Object call() throws Exception {
                return serviceImp.listMachineAndTml();
            }
        });
        Map<String, MachineAndTml> machineAndTmlMap = new HashMap<>();
        machineAndTmls.forEach(machineAndTml -> {
            machineAndTmlMap.put(machineAndTml.getFrameNum(), machineAndTml);
        });
        ArrayList<WorkRecordInfo> workRecordInfos = new ArrayList<>();
        WorkArea workArea;
        WorkRecordInfo info = new WorkRecordInfo();
        MachineAndTml machineAndTml;
        while (iterator.hasNext()) {
            workArea = iterator.next();
            machineAndTml = machineAndTmlMap.get(workArea.getMachineId());
            info.setIotNumber(machineAndTml.getTmlCode());
            //作业区域
            info.setRegionCode(Integer.parseInt(machineAndTml.getRegionCode()));

            //作业类型
            switch (machineAndTml.getMachineType()) {
                case "13":
                    info.setDicName("旋耕作业");
                    break;
                case "3":
                    info.setDicName("插秧作业");
                    break;
                case "1":
                    info.setDicName("深松作业");
                    break;
                default:
                    info.setDicName("收获作业");
                    break;
            }
            info.setMfrsArea(workArea.getGrossArea());
            info.setStartTime(workArea.getJobStartTime());
            info.setEndTime(workArea.getJobEndTime());
            try {
                workRecordInfos.add((WorkRecordInfo) info.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        ExecuteUtil.partitionRun(workRecordInfos, 200, eachList -> {
            try {
                String result = httpService.postToNXT(ConstantUtil.WorkRecordInfoUrl, eachList);
                WorkRecordInfoResult workRecordInfoResult = mapper.readValue(result, WorkRecordInfoResult.class);
                String msg = workRecordInfoResult.getMsg();
                String[] strings = msg.split("，");
                int length = strings.length;
                int errorCount = 0;
                for (int i = 0; i < length; i++) {
                    int index = strings[i].indexOf("条数据失败");
                    if (index != -1) {
                        String substring = strings[i].substring(0, index);
                        errorCount = Integer.parseInt(substring);
                    }
                }
                if (errorCount == 0) {
                    log.info(result);
                } else {
                    log.error("面积作业上传错误!!!\n" + result + "\n上报数据：" + eachList.toString());
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}
