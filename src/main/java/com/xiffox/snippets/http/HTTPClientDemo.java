package com.xiffox.snippets.http;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

public class HTTPClientDemo {
    private String url;
    private String requestBody;
    private String credentials;

    public HTTPClientDemo(String url, String requestBody, String credentials) {
        this.url = url;
        this.requestBody = requestBody;
        this.credentials = credentials;
    }

    public static void main(String[] args) {
        HTTPClientDemo demo = new HTTPClientDemo("https://some.example.rest/v1/api","",null);
        
        CloseableHttpClient client = null;
        try {

            SSLConnectionSocketFactory scsf = new SSLConnectionSocketFactory(
                    SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(),
                    NoopHostnameVerifier.INSTANCE);
            client = HttpClients.custom().setSSLSocketFactory(scsf).build();
            HttpPost httpPost = new HttpPost(demo.url);

            StringEntity entity = new StringEntity(demo.requestBody);
            httpPost.setEntity(entity);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            if (demo.credentials != null) {
                httpPost.setHeader("Authorization", "BASIC " + demo.credentials);
            }

            CloseableHttpResponse response = client.execute(httpPost);
            System.out.println(response);

        } catch (IOException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}