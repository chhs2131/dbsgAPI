package com.dbsgapi.dbsgapi.login.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class KakaoApiUserDto {
    @ApiParam(value="카카오 고유 ID", required=true)
    private String id;
    @JsonProperty("connected_at")
    private String connectedAt;

	/*
	private String nickname;

	@JsonProperty("properties")
	public void setProperties(Map<String, String> apiReturn) {
		this.nickname = apiReturn.get("nickname");
	}
	*/
}