package com.example.project.pharmacy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.project.api.dto.DocumentDto;
import com.example.project.api.dto.KakaoApiResponseDto;
import com.example.project.api.service.KakaoAddressSearchService;
import com.example.project.direction.dto.OutputDto;
import com.example.project.direction.entity.Direction;
import com.example.project.direction.service.Base62Service;
import com.example.project.direction.service.DirectionService;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacyRecommendationService {

	private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";

	private final KakaoAddressSearchService kakaoAddressSearchService;
	private final DirectionService directionService;
	private final Base62Service base62Service;

	@Value("${pharmacy.recommendation.base.url}")
	private String baseUrl;

	public List<OutputDto> recommendPharmacyList(String address) {

		KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

		if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
			log.error("[PharmacyRecommendationService.recommendPharmacyList fail] Input address: {}", address);
			return Collections.emptyList();
		}

		DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

		List<Direction> directionList = directionService.buildDirectionList(documentDto);
		//List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

		return directionService.saveAll(directionList)
			.stream()
			.map(this::convertToOutputDto)
			.collect(Collectors.toList());
	}

	private OutputDto convertToOutputDto(Direction direction) {

		return OutputDto.builder()
			.pharmacyName(direction.getTargetPharmacyName())
			.pharmacyAddress(direction.getTargetAddress())
			.directionUrl(baseUrl + base62Service.encodeDirectionId(direction.getId()))
			.roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
			.distance(String.format("%.2f km", direction.getDistance()))
			.build();
	}
}