package hongrok.spring.project1.controller;

//import lombok.Value;
import hongrok.spring.project1.data.dto.ShortUrlResponseDto;
import hongrok.spring.project1.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Null;

@RestController
@RequestMapping("/short-url")
public class ShortUrlController {

    private final Logger LOGGER = LoggerFactory.getLogger(ShortUrlController.class);
    //resources 의 application.properties 에서 값을 읽어옴
    @Value("${hongrok.spring.short.url.id}")
    private String CLIENT_ID;

    @Value("${hongrok.spring.short.url.secret}")
    private String CLIENT_SECRET;

    ShortUrlService shortUrlService;

    @Autowired
    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    //객체의(short url) 생명 주기: 어떻게 생성되고 어떻게 소멸될 것인가?
    //generate를 통해 생성되고,데이터 베이스에 저장될 것임
    //get을 할 때 값이 없으면 generate를 부른다거나..
    //위의 경우들을 고려해 개발을 해야함

    //shortUrlResponseDto는 정의에 따라 shortUrl과 originalUrl(orgUrl)을 담고 있는 객체임
    @PostMapping()
    public ShortUrlResponseDto generateShortUrl(String originalUrl) {

        LOGGER.info("[generateShortUrl] perform API. CLIENT_ID: {}, CLIENT_SECRET: {}", CLIENT_ID, CLIENT_SECRET);

        return shortUrlService.generateShortUrl(CLIENT_ID, CLIENT_SECRET, originalUrl);
    }

    @GetMapping()
    public ShortUrlResponseDto getShortUrl(String originalUrl) {
        ShortUrlResponseDto shortUrlResponseDto = new ShortUrlResponseDto("ss","ss");

        return shortUrlService.getShortUrl(CLIENT_ID, CLIENT_SECRET, originalUrl);
    }

    @PutMapping("/")
    public ShortUrlResponseDto updateShortUrl(String originalUrl) {
        return null;
    }

    @DeleteMapping
    public ShortUrlResponseDto deleteShortUrl(String url) {
        return null;
    }
}
