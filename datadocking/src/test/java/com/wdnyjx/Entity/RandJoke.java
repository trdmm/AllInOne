package com.wdnyjx.Entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.eventbus.Subscribe;
import lombok.Data;

@Data
public class RandJoke{

	@JsonProperty("result")
	private List<ResultItem> result;

	@JsonProperty("reason")
	private String reason;

	@JsonProperty("error_code")
	private int errorCode;

}