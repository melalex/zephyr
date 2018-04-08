package com.zephyr.test;

import com.zephyr.data.protocol.dto.UserAgentDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class UserAgents {

    public static final String WINDOWS_FIREFOX_ID = "0129eddf-a70b-43a6-ba46-5e47639ea404";
    public static final String WINDOWS_FIREFOX_HEADER =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0";
    public static final String FIREFOX = "Firefox";
    public static final String FIREFOX_VERSION = "59.0";
    public static final String WINDOWS = "Windows";
    public static final String WINDOWS_VERSION = "10";
    public static final String DESKTOP = "Desktop";

    public UserAgentDto windowsFirefox() {
        UserAgentDto result = new UserAgentDto();
        result.setId(WINDOWS_FIREFOX_ID);
        result.setHeader(WINDOWS_FIREFOX_HEADER);
        result.setBrowserName(FIREFOX);
        result.setBrowserVersion(FIREFOX_VERSION);
        result.setOsName(WINDOWS);
        result.setOsVersion(WINDOWS_VERSION);
        result.setDevice(DESKTOP);

        return result;
    }
}
