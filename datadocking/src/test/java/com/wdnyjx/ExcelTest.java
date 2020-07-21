package com.wdnyjx;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.alibaba.excel.EasyExcel;
import com.wdnyjx.Entity.Counts;
import com.wdnyjx.Entity.DT.AllMachine;
import com.wdnyjx.Entity.Excel.MachineInfo;
import com.wdnyjx.Entity.Excel.MachineInfoListener;
import com.wdnyjx.Service.IService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * File ...
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx
 * @author:OverLord
 * @Since:2020/6/20 17:19
 * @Version:v0.0.1
 */
@SpringBootTest
@Slf4j
public class ExcelTest {
    @Autowired
    IService serviceImp;
    @Qualifier("okhttp")
    OkHttpClient okHttpClient;

    /**
     * POI
     *
     * @throws Exception
     */
    @Test
    public void test01() throws Exception {
        InputStream inputStream = new FileInputStream("C:\\Users\\OverLord\\Desktop\\result.xlsx");

        //HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = workbook.getSheetAt(0);
        List<String> list = new ArrayList<>();
        for (Row row : sheet) {
            String frameNum = row.getCell(0).getStringCellValue();
            list.add(frameNum);
        }
        workbook.close();
        XSSFWorkbook workbook1 = new XSSFWorkbook();

        XSSFSheet countSheet = workbook1.createSheet();
        XSSFRow titleRow = countSheet.createRow(0);

        titleRow.createCell(0).setCellValue("frameNum");
        titleRow.createCell(1).setCellValue("total");
        titleRow.createCell(2).setCellValue("noGnss");

        int size = list.size();
        for (int i = 1; i < size; i++) {
            String frameNum = list.get(i);
            Counts counts = serviceImp.getCounts(frameNum);
            log.debug(counts.getTotalCount() + "   " + counts.getNoGnss());
            XSSFRow row = countSheet.createRow(i);
            row.createCell(0).setCellValue(frameNum);
            row.createCell(1).setCellValue(counts.getTotalCount());
            row.createCell(2).setCellValue(counts.getNoGnss());
        }
        workbook1.setSheetName(0, "counts");//设置sheet的Name

        FileOutputStream out = new FileOutputStream("C:\\Users\\OverLord\\Desktop\\result1.xlsx");
        workbook1.write(out);
        out.close();
    }

    @Test
    public void test02() throws IOException {
        FileInputStream fileIS = new FileInputStream("C:\\Users\\OverLord\\Desktop\\fuck.xlsx");
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileIS);
        XSSFSheet sheet0 = xssfWorkbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();
        sheet0.forEach(row -> {
            Cell cell0 = row.getCell(0);
            Cell cell1 = row.getCell(1);
            Cell cell2 = row.getCell(2);
            Cell cell3 = row.getCell(3);
            Cell cell4 = row.getCell(4);
            Cell cell5 = row.getCell(5);
            Cell cell6 = row.getCell(6);
/*            String frame_number = cell0.getStringCellValue();
            String tml_number = cell1.getStringCellValue();
            String status = cell2.getStringCellValue();
            String install_user = cell3.getStringCellValue();
            String install_user_show = cell4.getStringCellValue();
            String install_time = cell5.getStringCellValue();*/
            String frame_number = formatter.formatCellValue(cell0);
            String tml_number = formatter.formatCellValue(cell1);
            String status = formatter.formatCellValue(cell2);
            String install_user = formatter.formatCellValue(cell3);
            String install_user_show = formatter.formatCellValue(cell4);
            String install_time = formatter.formatCellValue(cell5);
            String fuck = formatter.formatCellValue(cell6);
            //Cell cell = row.createCell(6);
            //cell.setCellValue("fuck");
            log.debug(frame_number + " " + tml_number + " " + status + " " + install_user + " " + install_user_show + " " + install_time + "" + fuck);
        });
        //FileOutputStream out = new FileOutputStream("C:\\Users\\OverLord\\Desktop\\fuck.xlsx");
        //xssfWorkbook.write(out);
        fileIS.close();
        //out.close();
        xssfWorkbook.close();
    }

    /**
     * hutool下的excel操作
     */
    @Test
    public void test03() {
        File file = FileUtil.file("D:\\Personal\\Desktop\\07-16车联网车辆.xlsx");
        ExcelUtil.readBySax(file, 0, createRowHandler());
    }

    private RowHandler createRowHandler() {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                log.debug("[{}] [{}] {}", sheetIndex, rowIndex, rowlist);
            }
        };
    }

    /**
     * easyexcel
     */
    @Test
    public void test05() {
        File file = FileUtil.file("D:\\Personal\\Desktop\\07-16车联网车辆.xlsx");
        String fileName = file.getParent() + "\\copy" + System.currentTimeMillis() + ".xlsx";
        //不创建对象简单读
        //EasyExcel.read(file,new ExcelNoModelDataListener()).sheet().doRead();
        //创建对象读
        EasyExcel.read(file, MachineInfo.class, new MachineInfoListener(fileName)).sheet().doRead();
    }

    /**
     * 解析html
     */
    @Test
    public void test04() throws IOException {

        //File file = FileUtil.file("D:\\Personal\\Desktop\\dt2wm.html");
        File file1 = FileUtil.file("E:\\车联网+大田+赣机惠农\\小李遗产\\大田\\2020-07-20\\2020-07-20大田p1.html");
        File file2 = FileUtil.file("E:\\车联网+大田+赣机惠农\\小李遗产\\大田\\2020-07-20\\2020-07-20大田p2.html");
        Document document = Jsoup.parse(file1, "UTF-8");
        Document document2 = Jsoup.parse(file2, "UTF-8");
        Elements trs = document.select("table").select("tr");
        Elements trs2 = document2.select("table").select("tr");
        List<AllMachine> list = new ArrayList<>();
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
        trs2.forEach(tr -> {
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
        String fileName = "D:\\Personal\\Desktop\\大田需要绑定的车"+System.currentTimeMillis()+".xlsx";
        EasyExcel.write(fileName,AllMachine.class).sheet("2020-07-20新增").doWrite(list);
/*        Elements divs = document.getElementsByClass("category_table");
        Element div = divs.get(0);
        Elements tables = div.getElementsByTag("table");
        Element table = tables.get(0);
        Elements trs = table.getElementsByTag("tr");
        trs.forEach(tr -> {
            log.debug(tr.text());
        });*/
}}
