package com.zephyr.test;

import com.zephyr.data.protocol.dto.UserAgentDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class UserAgents {

    public static final String DESKTOP = "Desktop";

    public static final String WINDOWS_FIREFOX_ID = "0129eddf-a70b-43a6-ba46-5e47639ea404";
    public static final String WINDOWS_FIREFOX_HEADER =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:59.0) Gecko/20100101 Firefox/59.0";
    public static final String FIREFOX = "Firefox";
    public static final String FIREFOX_VERSION = "59.0";
    public static final String WINDOWS = "Windows";
    public static final String WINDOWS_VERSION = "10";

    public static final String MAC_SAFARI_ID = "df275cc1-6b59-406c-a700-062a38bde745";
    public static final String MAC_SAFARI_HEADER =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/601.7.7 (KHTML, like Gecko) Version/9.1.2 Safari/601.7.7";
    public static final String SAFARI = "Safari";
    public static final String SAFARI_VERSION = "9.1";
    public static final String MAC = "Mac OS X";
    public static final String MAC_VERSION = "10.11.6";

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

    public UserAgentDto macSafari() {
        UserAgentDto result = new UserAgentDto();
        result.setId(MAC_SAFARI_ID);
        result.setHeader(MAC_SAFARI_HEADER);
        result.setBrowserName(SAFARI);
        result.setBrowserVersion(SAFARI_VERSION);
        result.setOsName(MAC);
        result.setOsVersion(MAC_VERSION);
        result.setDevice(DESKTOP);

        return result;
    }
}
