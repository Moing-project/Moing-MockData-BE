package com.example.finalteammockdata.domain.social.service;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@Service
public class SocialServiceKakao implements SocialService{

    private final RestTemplate restTemplate;

    private final String KAKAO_URI_LOCAL = "http://localhost:3002/oauth/kakao";

    public SocialServiceKakao(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public String getAccessToken(String code) {

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type","authorization_code");
        parameters.add("client_id","71dfc97e339844f0f0a206cd8ba544ea");
        parameters.add("redirect_uri",KAKAO_URI_LOCAL);
        parameters.add("code",code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters,headers);

        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .build().toUri();

        ResponseEntity<String> requestEntity = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

        System.out.println("requestEntity.getBody() = " + requestEntity.getBody());

        return null;
    }
}
