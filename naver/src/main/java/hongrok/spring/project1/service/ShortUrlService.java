package hongrok.spring.project1.service;

import hongrok.spring.project1.data.dto.ShortUrlResponseDto;

public interface ShortUrlService {

    ShortUrlResponseDto getShortUrl(String clientId, String clientSecret, String originalUrl);

    ShortUrlResponseDto generateShortUrl(String clientId, String clientSecret, String originalUrl);

    ShortUrlResponseDto updateShortUrl(String clientId, String clientSecret, String originalUrl);

    ShortUrlResponseDto deleteByShortUrl(String clientId, String clientSecret, String originalUrl);

    ShortUrlResponseDto deleteByOriginalUrl(String clientId, String clientSecret, String originalUrl);

}
