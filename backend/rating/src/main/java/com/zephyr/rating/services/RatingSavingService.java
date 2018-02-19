package com.zephyr.rating.services;

import com.zephyr.rating.domain.Rating;

import java.util.List;

public interface RatingSavingService {

    void save(List<Rating> ratings);
}
