package com.pcr.service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Description(value = "Service for generating and validating OTP.")
@Service
public class OtpGenerator {

	private final Logger LOGGER = LoggerFactory.getLogger(OtpGenerator.class);
	
    private static final Integer EXPIRE_MIN = 5;
    private LoadingCache<String, Integer> otpCache;

    /**
     * Constructor configuration.
     */
    public OtpGenerator()
    {
        super();
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return 0;
                    }
                });
    }

    /**
     * Method for generating OTP and put it in cache.
     *
     * @param key - cache key
     * @return cache value (generated OTP number)
     */
    public Integer generateOTP(String key)
    {
    	LOGGER.info("Calling OtpGenerator generateOTP()... ");
        Random random = new Random();
        int OTP = 100000 + random.nextInt(900000);
        otpCache.put(key, OTP);
        LOGGER.info("Generated OTP : "+ OTP);
        return OTP;
    }

    /**
     * Method for getting OTP value by key.
     *
     * @param key - target key
     * @return OTP value
     */
    public Integer getOPTByKey(String key)
    {
    	LOGGER.info("Getting OTP From Cache ...");
        try {
            return otpCache.get(key);
        }
        catch (ExecutionException e) {
            return -1;
        }
    }

    /**
     * Method for removing key from cache.
     *
     * @param key - target key
     */
    public void clearOTPFromCache(String key) {
    	LOGGER.info("Clearing OTP From Cache...");
        otpCache.invalidate(key);
    }
}