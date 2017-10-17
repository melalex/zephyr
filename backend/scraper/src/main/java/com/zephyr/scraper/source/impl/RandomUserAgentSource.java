package com.zephyr.scraper.source.impl;

import com.google.common.collect.ImmutableList;
import com.zephyr.scraper.source.UserAgentSource;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandomUserAgentSource implements UserAgentSource {

    // TODO: Refactor when spring cloud config be able to serve Resources

    @Value("classpath:data/user-agents.txt")
    private Resource resource;

    private List<String> agents;

    @PostConstruct
    public void init() {
        ImmutableList.Builder<String> builder = ImmutableList.builder();

        @Cleanup Scanner scanner = agentScanner();

        while (scanner.hasNextLine()) {
            builder.add(scanner.nextLine());
        }

        agents = builder.build();
    }

    @Override
    public String provide() {
        return agents.get(randomIndex());
    }

    private int randomIndex() {
        return ThreadLocalRandom.current().nextInt(agents.size());
    }

    @SneakyThrows
    private Scanner agentScanner() {
        return new Scanner(resource.getFile());
    }
}
