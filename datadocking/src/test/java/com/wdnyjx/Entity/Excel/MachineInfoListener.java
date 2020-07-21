package com.wdnyjx.Entity.Excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MachineInfoListener extends AnalysisEventListener<MachineInfo> {
    private static final int BATCH_COUNT = 100;
    private final String fileName;
    private final WriteSheet writeSheet;
    List<MachineInfo> list = new ArrayList<>();
    private final ExcelWriter writer;

    public MachineInfoListener(String fileName) {
        this.fileName = fileName;
        writer = EasyExcel.write(fileName, MachineInfo.class).build();
        writeSheet = EasyExcel.writerSheet("真假美猴王").build();
    }

    @Override
    public void invoke(MachineInfo machineInfo, AnalysisContext analysisContext) {
        //log.debug(machineInfo.toString());
        list.add(machineInfo);
        if (list.size()>=BATCH_COUNT){
            write();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        write();
        log.debug("所有数据解析完成！");
        if (writer!=null){
            writer.finish();
        }
    }

    private void write() {
        //会覆盖之前的
        //EasyExcel.write(fileName,MachineInfo.class).sheet("副本").doWrite(list);
            writer.write(list,writeSheet);
    }
}
