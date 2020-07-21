package com.wdnyjx.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResultItem{

	@JsonProperty("unixtime")
	private int unixtime;

	@JsonProperty("hashId")
	private String hashId;

	@JsonProperty("content")
	private String content;
}