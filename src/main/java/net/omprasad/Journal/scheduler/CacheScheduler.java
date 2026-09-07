package net.omprasad.Journal.scheduler;

import net.omprasad.Journal.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class CacheScheduler {

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0/5 * 1/1 * ?")
    public void refreshAppCacheEvery5Min() {
        appCache.init();
    }
}
