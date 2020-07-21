package com.wdnyjx.Entity.gjhn;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * 面积汇报请求的返回信息
 */
@Data
public class WorkRecordInfoResult {

	@JsonProperty("msg")
	private String msg;

	@JsonProperty("code")
	private int code;

	@JsonProperty("Result")
	private String result;
}