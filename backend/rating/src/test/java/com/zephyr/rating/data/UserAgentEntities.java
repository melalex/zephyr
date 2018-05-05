package com.zephyr.rating.data;

import com.zephyr.rating.domain.UserAgent;
import com.zephyr.test.UserAgents;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class UserAgentEntities {

    public UserAgent windowsFirefox() {
        UserAgent result = new UserAgent();
        result.setBrowserName(UserAgents.FIREFOX);
        result.setOsName(UserAgents.WINDOWS);
        result.setDevice(UserAgents.DESKTOP);

        return result;
    }
}
