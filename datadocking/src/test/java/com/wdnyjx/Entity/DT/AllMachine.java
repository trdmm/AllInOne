package com.wdnyjx.Entity.DT;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 所有打了铭牌需要办理补贴的车子
 */
@Data
public class AllMachine {
    /**
     * <th width="5%">序号</th>
     * <th width="10%">大类</th>
     * <th width="10%">小类</th>
     * <th width="10%">品目</th>
     * <th width="10%">产品名称</th>
     * <th width="10%">产品型号</th>
     * <th width="15%">二维码</th>
     * <th width="10%">出厂编号</th>
     * <th width="10%">监控编号</th>
     * <th width="10%">操作</th>
     */
    @ExcelProperty("序号")
    private String 序号;
    @ExcelProperty("大类")
    private String 大类;
    @ExcelProperty("小类")
    private String 小类;
    @ExcelProperty("品目")
    private String 品目;
    @ExcelProperty("产品名称")
    private String 产品名称;
    @ExcelProperty("产品型号")
    private String 产品型号;
    @ExcelProperty("二维码")
    private String 二维码;
    @ExcelProperty("出厂编号")
    private String 出厂编号;
    @ExcelProperty("监控编号")
    private String 监控编号;
    @ExcelProperty("操作")
    private String 操作;
}
