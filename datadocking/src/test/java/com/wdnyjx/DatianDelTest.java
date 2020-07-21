package com.wdnyjx;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdnyjx.Entity.DT.AllMachine;
import com.wdnyjx.Entity.DT.DataItem;
import com.wdnyjx.Service.DTServiceImpl;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
public class DatianDelTest {

    private FormBody delDeviceBody;
    private Request delDeviceRequest;
    @Autowired
    DTServiceImpl dtService;

    @Test
    public void test01() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        //2898 500 500 500 500 500 398
        FormBody listDeviceBody = new FormBody.Builder()
                .add("page", "1")
                .add("limit", "158")
                .build();
        Request listDeviceRequest = new Request.Builder()
                .url("http://dev-iot.dtwl360.com/device/ajax/toListDevice.htm")
                .post(listDeviceBody)
                .header("Cookie", "JSESSIONID=5095ABD204E9C85DB5BF9ED018627552")
                .build();


        //JSESSIONID=5095ABD204E9C85DB5BF9ED018627552
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Response response = okHttpClient
                .newCall(listDeviceRequest)
                .execute();
        com.wdnyjx.Entity.DT.Response deviceLists = mapper.readValue(response.body().string(), com.wdnyjx.Entity.DT.Response.class);
        List<DataItem> data = deviceLists.getData();
        ArrayList<String> ids = new ArrayList<>();
        data.stream().forEach(dataItem -> {
            if (dataItem.getCreateTime() >= Long.parseLong("1593857673000")) {
                //7-4绑的
                ids.add(dataItem.getId());
            } else {
                log.error("全删完了");
                return;
            }
        });

        ids.forEach(id -> {
            delDeviceBody = new FormBody.Builder()
                    .add("id", id)
                    .build();
            delDeviceRequest = new Request.Builder()
                    .url("http://dev-iot.dtwl360.com/device/ajax/toDeleteDevice.htm")
                    .post(delDeviceBody)
                    .header("Cookie", "JSESSIONID=5095ABD204E9C85DB5BF9ED018627552")
                    .build();
            try {
                Response delResponse = okHttpClient.newCall(delDeviceRequest).execute();
                log.info(delResponse.body().string());
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        });

    }

    @Test
    public void test02() throws IOException {
        //String now = DateUtil.now();
        //DateTime dateTime = DateUtil.parse(now, "yyyy-MM-dd HH:mm:ss");
        String today = DateUtil.today();

        ExcelWriter writer=null;
        WriteSheet sheet = EasyExcel.writerSheet("所有车").build();
        List<AllMachine> list = new ArrayList<>();
        for (int page = 1; page < 443; page++) {
            Document doc = dtService.getDoc(page);
            Elements trs = doc.select("table").select("tr");
            trs.forEach(tr -> {
                        AllMachine machine = new AllMachine();
                        log.debug(tr.text());
                        Elements tds = tr.select("td");
                        int size = tds.size();
                        for (int i = 0; i < size; i++) {
                            Element td = tds.get(i);
                            switch (i) {
                                case 0:
                                    machine.set序号(td.text());
                                    break;
                                case 1:
                                    machine.set大类(td.text());
                                    break;
                                case 2:
                                    machine.set小类(td.text());
                                    break;
                                case 3:
                                    machine.set品目(td.text());
                                    break;
                                case 4:
                                    machine.set产品名称(td.text());
                                    break;
                                case 5:
                                    machine.set产品型号(td.text());
                                    break;
                                case 6:
                                    machine.set二维码(td.text());
                                    break;
                                case 7:
                                    machine.set出厂编号(td.text());
                                    break;
                                case 8:
                                    machine.set监控编号(td.text());
                                    break;
                                case 9:
                                    machine.set操作(td.text());
                                    break;
                                default:
                                    break;
                            }
                        }
                        if (machine.get序号()!=null){
                            log.debug(machine.toString());
                            list.add(machine);
                        }
                    }
            );
        }
        String fileName = "D:\\Personal\\Desktop\\大田需要绑定的车"+today+".xlsx";
        EasyExcel.write(fileName,AllMachine.class).sheet("所有车").doWrite(list);
    }
}
