package com.pcr.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * @author 513365
 * Oct 7, 2020
 */
@Service
public class OtpService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	private static final Integer EXPIRE_MINS = 1;
	private LoadingCache<String, Integer> otpCache;
	 
	 public OtpService(){
		 super();
		 otpCache = CacheBuilder.newBuilder().
			     expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
				      public Integer load(String key) {
				             return 0;
				       }
				   });
	 }
	 
	//This method is used to push the opt number against Key. Rewrite the OTP if it exists
	 //Using user id  as key
	 public int generateOTP(String key){
		 
		Random random = new Random();	
		int otp = 100000 + random.nextInt(900000);
		otpCache.put(key, otp);
		logger.info("In generateOTP() otp : "+ otp);
		return otp;
	 }
		 
	 //This method is used to return the OPT number against Key->Key values is username
	 public int getOtp(String key){		 
		try{
			logger.info("In getOtp() for key : "+ key);
			 return otpCache.get(key); 
		}catch (Exception e){
		 return 0;			 
		}
	 }
		 
	//This method is used to clear the OTP catched already
	public void clearOTP(String key){		
		logger.info("In clearOTP() for key : "+ key);
		 otpCache.invalidate(key);
	 }
	
	/**
     * Method for validating provided OTP
     *
     * @param key - provided key
     * @param otpNumber - provided OTP number
     * @return boolean value (true|false)
     */
    public Boolean validateOTP(String key, Integer otpNumber)
    {
        // get OTP from cache
        Integer cacheOTP = getOtp(key);
        if (cacheOTP.equals(otpNumber))
        {
        	//otpCache.invalidate(key);
            return true;
        }
        return false;
    }
    
}