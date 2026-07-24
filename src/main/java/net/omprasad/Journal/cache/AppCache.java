package net.omprasad.Journal.cache;


import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.omprasad.Journal.entity.CacheEntity;
import net.omprasad.Journal.repository.CacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class AppCache {

    public enum keys {
        WEATHER_API;
    }

    @Autowired
    private CacheRepository cacheRepository;

    @Getter
    private Map<String, String> cache;

    @PostConstruct
    public void init() {
        cache = new HashMap<>();
        List<CacheEntity> all = cacheRepository.findAll();
        log.info("Inside cache, List: {}", all);
        for(CacheEntity ch: all) {
            cache.put(ch.getKey(), ch.getValue());
        }
    }

}
