package com.wdnyjx.Entity.gjhn;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 面积上报信息
 */
@Data
public class WorkRecordInfo implements Cloneable{

	/**
	 * 作业行政区域
	 */
	@JsonProperty("regionCode")
	private int regionCode;

	/**
	 * 物联网设备号
	 */
	@JsonProperty("iotNumber")
	private String iotNumber;

	/**
	 * 开始时间。例： 2019-01-17 00:00:00
	 */
	@JsonProperty("startTime")
	private String startTime;

	/**
	 * 结束时间。例： 2019-01-17 00:00:00
	 */
	@JsonProperty("endTime")
	private String endTime;

	/**
	 * 作业类型。如：
	 * 深松作业、播种作业、深翻作业、收获作业、植保作业、插秧作业、秸秆还田作业、旋耕作业
	 */
	@JsonProperty("dicName")
	private String dicName;

	/**
	 * 厂家代码
	 */
	@JsonProperty("compCode")
	private String compCode="121";

	/**
	 * 作业面积
	 */
	@JsonProperty("mfrsArea")
	private String mfrsArea;

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}