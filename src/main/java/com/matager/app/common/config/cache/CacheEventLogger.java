/*
 * @Abdullah Sallam
 */

package com.matager.app.common.config.cache;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Slf4j
public class CacheEventLogger implements CacheEventListener<Object, Object> {

    @Override
    public void onEvent(CacheEvent<?, ?> event) {
        log.info("Cache " + event.getKey() + " " + event.getOldValue() + " " + event.getNewValue());
    }
}
