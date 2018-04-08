package com.zephyr.rating.business.services;

import com.zephyr.commons.support.Indexed;
import com.zephyr.rating.core.domain.Request;
import reactor.util.function.Tuple2;

import java.util.List;

public interface RatingSavingService {

    void save(Tuple2<Request, List<Indexed<String>>> ratings);
}
