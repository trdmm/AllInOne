package com.wdnyjx.Entity.gjhn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 实时位置,历史位置
 *
 * code int 状态码
 * 			0：失败; 100：成功
 * msg String 消息提示
 *			  1、成功
 *			  2、N 台设备上报成功,N 台设备不存在，请上报农机信息接口注册物联网设备
 *			  3、发生故障，请联系平台管理员
 * data String 返回内容
 *			  1、上报位置数据成功
 *			  2、如果不存在的设备数量小于等于10，则返回不存在的设备序列号
 */
public class ReportPointResult{

	@JsonProperty("msg")
	private String msg;

	@JsonProperty("code")
	private int code;

	@JsonProperty("data")
	private String data;
}