package com.zephyr.proxy.services.jobs;

import com.zephyr.proxy.services.ProxyService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RefreshScope
public class ResetFailedProxiesJob {

    @Setter(onMethod = @__(@Autowired))
    private ProxyService proxyService;

    @Scheduled(cron = "${proxy.resetFailedCron}")
    public void perform() {
        log.info("Performing ResetFailedProxiesJob");

        proxyService.resetFailedProxies()
                .subscribe();
    }
}
