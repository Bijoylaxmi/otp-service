package com.pcr.service;


//import com.starter.springboot.rest.dto.EmailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

@Description(value = "Service responsible for handling OTP related functionality.")
@Service
public class OtpResourceService {

    private final Logger LOGGER = LoggerFactory.getLogger(OtpResourceService.class);

    private OtpGenerator otpGenerator;
//    private EmailService emailService;
//    private UserService userService;

    /**
     * Constructor dependency injector
     * @param otpGenerator - otpGenerator dependency
     * @param emailService - email service dependency
     * @param userService - user service dependency
     */
    @Autowired
    public OtpResourceService(OtpGenerator otpGenerator)
    {
    	super();
        this.otpGenerator = otpGenerator;
//        this.emailService = emailService;
//        this.userService = userService;
    }

    /**
     * Method for generate OTP number
     *
     * @param key - provided key (username in this case)
     * @return boolean value (true|false)
     */
    public int generateOtp(String key)
    {
        // generate otp
    	Boolean otpCheck = true;
        Integer otpValue = otpGenerator.generateOTP(key);
        if (otpValue == -1)
        {
            LOGGER.error("OTP generator is not working...");
            otpCheck = false;
            //return  false;
        }

        LOGGER.info("Generated OTP: {}", otpValue);
		return otpValue;

        // fetch user e-mail from database
//        String userEmail = userService.findEmailByUsername(key);
//        List<String> recipients = new ArrayList<>();
//        recipients.add(userEmail);

        // generate emailDTO object
//        EmailDTO emailDTO = new EmailDTO();
//        emailDTO.setSubject("Spring Boot OTP Password.");
//        emailDTO.setBody("OTP Password: " + otpValue);
//        emailDTO.setRecipients(recipients);

        // send generated e-mail
      //  return emailService.sendSimpleMessage(emailDTO);
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
        Integer cacheOTP = otpGenerator.getOPTByKey(key);
        if (cacheOTP.equals(otpNumber))
        {
            otpGenerator.clearOTPFromCache(key);
            return true;
        }
        return false;
    }
}
