package com.example.project.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoApiResponseDto {

	@JsonProperty("meta")
	private MetaDto metaDto;

	@JsonProperty("documents")
	private List<DocumentDto> documentList;
}
