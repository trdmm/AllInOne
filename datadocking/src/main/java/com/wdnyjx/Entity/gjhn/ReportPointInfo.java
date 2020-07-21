package com.wdnyjx.Entity.gjhn;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 实时位置和历史位置
 */
@Data
public class ReportPointInfo{

	/**
	 * 物联网设备号
	 */
	@JsonProperty(" serialNumber")
	private String serialNumber;

	/**
	 * 上报时间戳(单位：毫秒)
	 */
	@JsonProperty("reportDate")
	private long reportDate;

	/**
	 * 速度(单位：公里/小时)
	 */
	@JsonProperty(" speed")
	private double speed;

	/**
	 * 海拔(单位：米)
	 */
	@JsonProperty("altitude")
	private int altitude;

	/**
	 * 朝向
	 */
	@JsonProperty("orientation")
	private int orientation;

	/**
	 * 纬度
	 */
	@JsonProperty("latitude")
	private double latitude;

	/**
	 * 厂家代码
	 */
	@JsonProperty("compCode")
	private String compCode;

	/**
	 * 经度
	 */
	@JsonProperty("longitude")
	private double longitude;
}