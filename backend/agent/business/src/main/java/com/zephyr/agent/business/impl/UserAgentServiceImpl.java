package com.zephyr.agent.business.impl;

import com.zephyr.agent.business.UserAgentService;
import com.zephyr.agent.core.repositories.UserAgentRepository;
import com.zephyr.commons.ListUtils;
import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.protocol.dto.UserAgentDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserAgentServiceImpl implements UserAgentService {

    @Setter(onMethod = @__(@Autowired))
    private UserAgentRepository userAgentRepository;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public Mono<UserAgentDto> findOneByExample(String device, String osName, String browser) {
        return userAgentRepository.findAllByDeviceAndOsNameAndBrowserName(device, osName, browser)
                .collectList()
                .map(ListUtils::random)
                .map(mapper.mapperFor(UserAgentDto.class));
    }
}
