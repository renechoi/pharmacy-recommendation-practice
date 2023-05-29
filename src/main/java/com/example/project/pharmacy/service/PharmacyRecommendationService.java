package com.example.project.pharmacy.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.project.api.dto.DocumentDto;
import com.example.project.api.dto.KakaoApiResponseDto;
import com.example.project.api.service.KakaoAddressSearchService;
import com.example.project.direction.entity.Direction;
import com.example.project.direction.service.DirectionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacyRecommendationService {

	private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";

	private final KakaoAddressSearchService kakaoAddressSearchService;
	private final DirectionService directionService;

	@Value("${pharmacy.recommendation.base.url}")
	private String baseUrl;

	public void recommendPharmacyList(String address) {

		KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

		if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
			log.error("[PharmacyRecommendationService.recommendPharmacyList fail] Input address: {}", address);
			return;
		}

		DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

		// List<Direction> directionList = directionService.buildDirectionList(documentDto);
		List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

		directionService.saveAll(directionList);
	}
}