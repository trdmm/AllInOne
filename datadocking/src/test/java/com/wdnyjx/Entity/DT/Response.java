package com.wdnyjx.Entity.DT;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Response{

	@JsonProperty("total")
	private int total;

	@JsonProperty("code")
	private String code;

	@JsonProperty("data")
	private List<DataItem> data;

	@JsonProperty("errCode")
	private String errCode;

	@JsonProperty("errMsg")
	private String errMsg;

	@JsonProperty("status")
	private String status;
}