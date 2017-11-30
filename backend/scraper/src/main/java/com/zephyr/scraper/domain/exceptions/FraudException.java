package com.zephyr.scraper.domain.exceptions;

import com.zephyr.scraper.domain.EngineResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class FraudException extends RuntimeException {
    private static final long serialVersionUID = -7579427673806004192L;

    @Getter
    private EngineResponse response;
}