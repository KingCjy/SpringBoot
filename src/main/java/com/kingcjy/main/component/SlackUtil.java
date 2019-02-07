package com.kingcjy.main.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Component
public class SlackUtil {
    public static final String DEFAULT_CHANNEL = "";
    public static final String DEFAULT_USERNAME = "";

    @Value("slackWebHookUrl")
    private String slackWebHookUrl;

    public void sendSlack(String text) {
        sendSlack(DEFAULT_CHANNEL, DEFAULT_USERNAME, text);
    }
    public void sendSlack(String username, String text) {
        sendSlack(DEFAULT_CHANNEL, username, text);
    }
    public void sendSlack(String channel, String username, String text) {
        String body =   "{                                          " +
                        "    \"channel\": \"" + channel+ "\"        " +
                        "  , \"username\": \"" + username + "\"     " +
                        "  , \"text\": \"" + text + "\"             " +
                        "}                                          ";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application", "json"));
        HttpEntity<?> requestEntity = new HttpEntity<Object>(body, requestHeaders);

        ResponseEntity<String> result = restTemplate.exchange(slackWebHookUrl, HttpMethod.POST, requestEntity, String.class);
    }
}
