package com.finalproject.assetmanagement.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    private static final Logger logger = LoggerFactory.getLogger(MyService.class);

    public void doSomething() {
        logger.info("Do Something...");
        // Log level lain: debug, warn, error, dll.
    }
}
