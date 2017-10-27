package com.zephyr.scraper.loader.exceptions;

import com.zephyr.scraper.loader.context.model.RequestContext;

public class RequestException extends RuntimeException {
    private static final long serialVersionUID = -4214987745593759340L;

    private RequestContext failedRequest;

    public RequestContext getFailedRequest() {
        return failedRequest;
    }

    public RequestException(Throwable cause, RequestContext failedRequest) {
        super(cause);
        this.failedRequest = failedRequest;
    }
}