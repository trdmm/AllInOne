package com.wdnyjx.Entity.Excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelNoModelDataListener extends AnalysisEventListener<Map<String, String>> {
    private static final int BATCH_COUNT = 100;
    List<Map> list = new ArrayList<>();

    @Override
    public void invoke(Map<String, String> s, AnalysisContext analysisContext) {
        log.debug(s.toString());
        list.add(s);
        if (list.size()>=BATCH_COUNT){
            toast();
            list.clear();
        }
    }

    private void toast() {
        log.debug("{}条数据，开始存储数据库！", list.size());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        toast();
        log.debug("所有数据解析完成！");
    }
}
