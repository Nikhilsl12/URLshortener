package org.coderscrib.urlshortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UrlController {

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");

        if (originalUrl == null || originalUrl.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "URL is required"));
        }
    // If the http not given then it automatically adds it.
        if (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")) {
            originalUrl = "http://" + originalUrl;
        }

        String shortCode = urlService.shortenUrl(originalUrl);

        return ResponseEntity.ok(Map.of(
                "originalUrl", originalUrl,
                "shortCode", shortCode,
                "shortUrl", "/api/" + shortCode
        ));
    }

    @GetMapping("/{shortCode}")
    public Object redirectToOriginalUrl(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);

        return (originalUrl != null)
                ? new RedirectView(originalUrl)
                : ResponseEntity.status(404).body(Map.of("error", "Short URL not found"));
    }
}
