package com.zephyr.agent.services.impl;

import com.zephyr.agent.domain.UserAgent;
import com.zephyr.agent.repositories.UserAgentRepository;
import com.zephyr.agent.services.UserAgentService;
import com.zephyr.commons.ListUtils;
import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.protocol.dto.UserAgentDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
        var example = new UserAgent();
        example.setDevice(device);
        example.setDevice(osName);
        example.setDevice(browser);

        return userAgentRepository.findAll(Example.of(example))
                .collectList()
                .map(ListUtils::random)
                .map(mapper.mapperFor(UserAgentDto.class));
    }
}
