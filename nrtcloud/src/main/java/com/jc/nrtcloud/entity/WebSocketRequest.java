package com.jc.nrtcloud.entity;

import lombok.Data;

import java.util.Map;

@Data
public class WebSocketRequest {
    private String method;
    private String uri;
    private Map<String, String> headers;
    private String token;
}
