package com.dbsgapi.dbsgapi.api.login.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "카카오 사용자 정보")
@Data
public class KakaoApiUserDto {
    @Schema(name = "카카오 고유 ID", required = true)
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