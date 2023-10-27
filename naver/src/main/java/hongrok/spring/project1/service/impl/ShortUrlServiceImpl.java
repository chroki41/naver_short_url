package hongrok.spring.project1.service.impl;

import hongrok.spring.project1.controller.ShortUrlController;
import hongrok.spring.project1.data.dao.ShortUrlDAO;
import hongrok.spring.project1.data.dto.NaverUriDto;
import hongrok.spring.project1.data.dto.ShortUrlResponseDto;
import hongrok.spring.project1.data.entity.ShortUrlEntity;
import hongrok.spring.project1.repository.ShortUrlRepository;
import hongrok.spring.project1.service.ShortUrlService;
import lombok.Setter;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    private final Logger LOGGER = LoggerFactory.getLogger(ShortUrlController.class);
    private final ShortUrlDAO shortUrlDAO;
//    private final ShortUrlRepository shortUrlRepository;

//    @Autowired
//    public ShortUrlServiceImpl(ShortUrlDAO shortUrlDAO, ShortUrlRepository shortUrlRepository) {
//        this.shortUrlDAO = shortUrlDAO;
//        this.shortUrlRepository = shortUrlRepository;
//    }

    @Autowired
    public ShortUrlServiceImpl(ShortUrlDAO shortUrlDAO) {
        this.shortUrlDAO = shortUrlDAO;
    }
    //override는 해당 파일 위에 있는 interface에 선언되있는 함수를 제대로 정의해주기 위한 것
    @Override
    public ShortUrlResponseDto generateShortUrl(String clientId, String clientSecret, String originalUrl) {

        LOGGER.info("[generateShortUrl] request data: {}", originalUrl);

        ResponseEntity<NaverUriDto> responseEntity = requestShortUrl(clientId, clientSecret, originalUrl);

        String orgUrl = responseEntity.getBody().getResult().getOrgUrl();
        String shortUrl = responseEntity.getBody().getResult().getUrl();
        String hash = responseEntity.getBody().getResult().getHash();

        //entity에 아래의 값들을 넣어줘서 초기화해줌
        ShortUrlEntity shortUrlEntity = new ShortUrlEntity();
        shortUrlEntity.setOrgUrl(orgUrl);
        shortUrlEntity.setUrl(shortUrl);
        shortUrlEntity.setHash(hash);

        shortUrlDAO.saveShortUrl(shortUrlEntity);

        ShortUrlResponseDto shortUrlResponseDto = new ShortUrlResponseDto(orgUrl, shortUrl);
        LOGGER.info("[generateShortUrl] Response DTO: {}", shortUrlResponseDto.toString());
        return shortUrlResponseDto;
    }

    @Override
    public ShortUrlResponseDto getShortUrl(String clientId, String clientSecret, String originalUrl) {

        LOGGER.info("[getShortUrl] request data : {}", originalUrl);
        ShortUrlEntity getShortUrlEntity  = shortUrlDAO.getShortUrl(originalUrl);

        String orgUrl;
        String shortUrl;

        if (getShortUrlEntity == null) {
            LOGGER.info("[getShortUrl] No Entity in Database");
            ResponseEntity<NaverUriDto> responseEntity = requestShortUrl(clientId, clientSecret, originalUrl);

            orgUrl = responseEntity.getBody().getResult().getOrgUrl();
            shortUrl = responseEntity.getBody().getResult().getUrl();
        } else {
            orgUrl = getShortUrlEntity.getOrgUrl();
            shortUrl = getShortUrlEntity.getUrl();
        }

        ShortUrlResponseDto shortUrlResponseDto = new ShortUrlResponseDto(orgUrl, shortUrl);

        LOGGER.info("[getShortUrl] Response DTO : {}", shortUrlResponseDto.toString());
        return shortUrlResponseDto;
    }

    @Override
    public ShortUrlResponseDto updateShortUrl(String clientId, String clientSecret, String originalUrl) {
        return null;
    }

    @Override
    public ShortUrlResponseDto deleteByShortUrl(String clientId, String clientSecret, String originalUrl) {
        return null;
    }

    @Override
    public ShortUrlResponseDto deleteByOriginalUrl(String clientId, String clientSecret, String originalUrl) {
        return null;
    }

    private ResponseEntity<NaverUriDto> requestShortUrl(String clientID, String clientSecret, String originalUrl) {
        LOGGER.info("[requestShortUrl] client ID : ***, client Secret : ***, original Url : {}", originalUrl);

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/util/shorturl")
                .queryParam("url", originalUrl)
                .encode()
                .build()
                .toUri();

        LOGGER.info("[requestShortUrl] set HTTP Request Header");
        //header를 세팅해줌
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Naver-Client-Id", clientID);
        headers.set("X-Naver-Client-Secret", clientSecret);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        RestTemplate restTemplate = new RestTemplate();
        //resttemplate을 초기화하고, exchange를 통해 naver로 보낸 후 naveruridto형태로 결과값을 받음
        LOGGER.info("[requestShortUrl] request by restTemplate");
        ResponseEntity<NaverUriDto> responseEntity = restTemplate.exchange(uri, HttpMethod.GET,
                entity, NaverUriDto.class);

        LOGGER.info("[requestShortUrl request has been successfully complete.");

        return responseEntity;
    }
}
