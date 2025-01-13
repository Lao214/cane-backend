package com.example.integratedHub.utils;

/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2023/8/11 4:53 PM
 */
public class UploadResponse {
    private String minIoUrl;

    private String nginxUrl;

    public UploadResponse() {
    }

    public UploadResponse(String minIoUrl, String nginxUrl) {
        this.minIoUrl = minIoUrl;
        this.nginxUrl = nginxUrl;
    }

    public String getMinIoUrl() {
        return minIoUrl;
    }

    public void setMinIoUrl(String minIoUrl) {
        this.minIoUrl = minIoUrl;
    }

    public String getNginxUrl() {
        return nginxUrl;
    }

    public void setNginxUrl(String nginxUrl) {
        this.nginxUrl = nginxUrl;
    }
}

