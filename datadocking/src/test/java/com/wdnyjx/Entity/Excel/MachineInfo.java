package com.wdnyjx.Entity.Excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 暂时不用,先用eastexcel的最简单的读
 */
@Data
public class MachineInfo {
    /**
     * 车辆类型	所属机构	驾驶员姓名	驾驶员电话	机具名称
     * 品牌	出厂编号	发动机号	车架号	车牌号	终端	生产时间
     * 车辆功率	购机时间	车辆宽幅	活动标志	状态	绑定人	绑定时间
     */
    @ExcelProperty("车辆类型")
    private String 车辆类型;
    @ExcelIgnore
    private String 所属机构;
    @ExcelIgnore
    private String 驾驶员姓名;
    @ExcelIgnore
    private String 驾驶员电话;
    @ExcelProperty("机具名称")
    private String 机具名称;
    @ExcelIgnore
    private String 品牌;
    @ExcelProperty("出厂编号")
    private String 出厂编号;
    @ExcelProperty("发动机号")
    private String 发动机号;
    @ExcelIgnore
    private String 车架号;
    @ExcelIgnore
    private String 车牌号;
    @ExcelProperty("终端")
    private String 终端;
    @ExcelIgnore
    private String 生产时间;
    @ExcelIgnore
    private String 车辆功率;
    @ExcelIgnore
    private String 购机时间;
    @ExcelIgnore
    private String 车辆宽幅;
    @ExcelIgnore
    private String 活动标志;
    @ExcelIgnore
    private String 状态;
    @ExcelIgnore
    private String 绑定人;
    @ExcelIgnore
    private String 绑定时间;
}
