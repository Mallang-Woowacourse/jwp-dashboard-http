package org.apache.coyote.http11.request;

import java.util.Arrays;
import java.util.List;

public class HttpRequest {

    private final StartLine startLine;
    private final List<Header> headers;
    private final Body body;

    private HttpRequest(StartLine startLine, List<Header> headers, Body body) {
        this.startLine = startLine;
        this.headers = headers;
        this.body = body;
    }

    public static HttpRequestBuilder builder() {
        return new HttpRequestBuilder();
    }

    public StartLine startLine() {
        return startLine;
    }

    public List<Header> headers() {
        return headers;
    }

    public Body body() {
        return body;
    }

    public static class HttpRequestBuilder {

        private StartLine startLine;
        private List<Header> headers;
        private Body body;

        public HttpRequestBuilder startLine(StartLine startLine) {
            this.startLine = startLine;
            return this;
        }

        public HttpRequestBuilder headers(List<Header> headers) {
            this.headers = headers;
            return this;
        }

        public HttpRequestBuilder headers(Header... headers) {
            this.headers = Arrays.asList(headers);
            return this;
        }

        public HttpRequestBuilder body(Body body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(startLine, headers, body);
        }
    }
}