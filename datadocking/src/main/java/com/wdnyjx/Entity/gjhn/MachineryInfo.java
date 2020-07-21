package com.wdnyjx.Entity.gjhn;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 车辆基础信息
 */
@Data
public class MachineryInfo{

	/**
	 * 机具型号
	 */
	@JsonProperty("machineryModel")
	private String machineryModel;

	/**
	 * 机具名称|机具型号
	 */
/*	@JsonProperty("remarks")
	private String remarks;*/

	/**
	 * 车主电话
	 */
	@JsonProperty("ownerIphone")
	private String ownerIphone="无";

	/**
	 * 农机生产企业名称
	 */
	@JsonProperty("companyName")
	private String companyName="江苏沃得农业机械股份有限公司";

	/**
	 * 发动机号
	 */
	@JsonProperty("machineryEngine")
	private String machineryEngine;

	/**
	 * 出厂编号
	 */
	@JsonProperty("factoryNumber")
	private String factoryNumber;

	/**
	 * 厂家代码 121
	 */
	@JsonProperty("compCode")
	private String compCode="121";

	/**
	 * 农机操作人
	 */
	@JsonProperty("operator")
	private String operator="无";

	/**
	 * 机具名称
	 */
	@JsonProperty("modelName")
	private String modelName;

	/**
	 * 行政区域
	 * 360123
	 * (到县级，可以是县-区-地级市) (见备注6)
	 */
	@JsonProperty("regionCode")
	private int regionCode;

	/**
	 * 物联网设备号
	 */
	@JsonProperty("iotNumber")
	private String iotNumber;

	/**
	 * 底盘号/车架号
	 */
	@JsonProperty("drivingNumber")
	private String drivingNumber;

	/**
	 * 车主或合作社名称
	 */
	@JsonProperty("ownerName")
	private String ownerName="无";

	/**
	 * 具体的农机类型，不能随意填写或填错，如有疑问联系接口对接人。
	 * 如：深松旋耕机、水稻插秧机、植保无人机，等
	 */
	@JsonProperty("dicName")
	private String dicName;

	/**
	 * 车主身份证号
	 */
	@JsonProperty("idCard")
	private String idCard="无";
}