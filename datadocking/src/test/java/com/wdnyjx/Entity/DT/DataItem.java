package com.wdnyjx.Entity.DT;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DataItem{

	@JsonProperty("deviceType")
	private int deviceType;

	@JsonProperty("flag")
	private Object flag;

	@JsonProperty("uptTime")
	private Object uptTime;

	@JsonProperty("uptUser")
	private Object uptUser;

	@JsonProperty("updateUserName")
	private Object updateUserName;

	@JsonProperty("cTime")
	private Object cTime;

	@JsonProperty("updateTime")
	private Object updateTime;

	@JsonProperty("createUserName")
	private Object createUserName;

	@JsonProperty("type")
	private Object type;

	@JsonProperty("token")
	private String token;

	@JsonProperty("applyId")
	private Object applyId;

	@JsonProperty("cUser")
	private Object cUser;

	@JsonProperty("createTime")
	private long createTime;

	@JsonProperty("imei")
	private String imei;

	@JsonProperty("id")
	private String id;

	@JsonProperty("isDel")
	private Object isDel;

	@JsonProperty("status")
	private Object status;
}