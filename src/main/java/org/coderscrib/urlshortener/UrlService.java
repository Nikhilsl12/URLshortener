package org.coderscrib.urlshortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class UrlService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_CODE_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();
    
    private final UrlRepository urlRepository;
    
    @Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String originalUrl) {

        String shortCode = generateUniqueShortCode();
        
        // Create and save the URL entity
        UrlEntity urlEntity = new UrlEntity(originalUrl, shortCode);
        urlRepository.save(urlEntity);
        
        return shortCode;
    }

    public String getOriginalUrl(String shortCode) {
        Optional<UrlEntity> urlEntityOptional = urlRepository.findByShortCode(shortCode);
        
        if (urlEntityOptional.isPresent()) {
            UrlEntity urlEntity = urlEntityOptional.get();
            // Increment the access count
            urlEntity.incrementAccessCount();
            urlRepository.save(urlEntity);
            return urlEntity.getOriginalUrl();
        }
        
        return null;
    }
    // checking that shortcode doesn't already exits .
    private String generateUniqueShortCode() {
        String shortCode;
        do {
            shortCode = generateRandomShortCode();
        } while (urlRepository.existsByShortCode(shortCode));
        
        return shortCode;
    }

    private String generateRandomShortCode() {
        StringBuilder sb = new StringBuilder(SHORT_CODE_LENGTH);
        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
}