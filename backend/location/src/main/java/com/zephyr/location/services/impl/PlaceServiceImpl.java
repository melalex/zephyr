package com.zephyr.location.services.impl;

import static com.zephyr.commons.StreamUtils.concat;
import static com.zephyr.commons.StreamUtils.range;

import com.zephyr.commons.FunctionUtils;
import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.protocol.dto.PlaceDto;
import com.zephyr.location.domain.Place;
import com.zephyr.location.repositories.PlaceRepository;
import com.zephyr.location.services.PlaceService;
import com.zephyr.location.util.Exceptions;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private static final String UULE_FORMAT = "w+CAIQICI%s%s";

    private static final int DEFAULT_DEPTH = 1;

    private List<String> secretMap;

    @NonNull
    private PlaceRepository placeRepository;

    @NonNull
    private ExtendedMapper mapper;

    @PostConstruct
    public void init() {
        this.secretMap = concat(range('A', 'Z'), range('a', 'z'), range('0', '9'), Stream.of("-", "_"))
                .collect(Collectors.toList());
    }

    @Override
    public PlaceDto findById(long id) {
        return placeRepository.findById(id, DEFAULT_DEPTH)
                .map(toDto())
                .orElseThrow(Exceptions.newNotFoundError(Place.class, id));
    }

    @Override
    public Set<PlaceDto> findAllByCountryIsoAndNameContains(String iso, String name) {
        return placeRepository.findAllByCountryIsoAndNameContainsIgnoreCase(iso, name)
                .map(toDto())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<PlaceDto> findAllByCanonicalNameContains(String name, Pageable pageable) {
        return placeRepository.findAllByCanonicalNameContains(name, pageable)
                .map(toDto())
                .collect(Collectors.toSet());
    }

    private Function<Place, PlaceDto> toDto() {
        return mapper.<Place, PlaceDto>mapperFor(PlaceDto.class)
                .andThen(FunctionUtils.onNext(p -> p.setUule(generateUule(p))));
    }

    private String generateUule(PlaceDto place) {
        String canonicalName = place.getCanonicalName();
        String encodeCanonicalName = Base64.encodeBase64String(canonicalName.getBytes());

        String secretKey = secretMap.get(canonicalName.length() % secretMap.size());

        return StringUtils.strip(String.format(UULE_FORMAT, secretKey, encodeCanonicalName), "=");
    }
}
