package com.pcr.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pcr.constant.OtpConstant;
import com.pcr.dto.OtpDTO;
import com.pcr.service.OtpResourceService;

@Description(value = "Resource for generating and validating OTP requests.")
@RestController
@RequestMapping("/api/otp")
public class OTPResourceController {

	private final Logger LOGGER = LoggerFactory.getLogger(OTPResourceController.class);
	
    private OtpResourceService otpService;
    //private OtpDTO otpDto;

    /**
     * Constructor dependency injector.
     * @param otpService - otp service
     */
    @Autowired
    public OTPResourceController(OtpResourceService otpService) {
    	super();
        this.otpService = otpService;
        //this.otpDto = otpDto;
    }

    @PostMapping(value = "generate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> generateOTP()
    {
    	LOGGER.info("Calling generateOTP...");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Map<String, Object> response = new HashMap<>(2);

        // check authentication
        if (username == null) {
        	LOGGER.info("Unauthorized access...");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // generate OTP.
        int otp = otpService.generateOtp(username);
        LOGGER.info("Actual otp {int} :: "+ otp);
        //String otpStr = Integer.toString(otp);
        LOGGER.info("Actual otp {String} :: "+ otp);
        //Boolean isGenerated = otpService.generateOtp(username);
        if (otp < 0)
        {
            response.put("status", "error");
            response.put("message", "OTP can not be generated.");
            LOGGER.info("response for invalid otp :: "+ response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // success message
        response.put("otp", otp);
        response.put("status", "success");
        response.put("message", "OTP successfully generated");
        LOGGER.info("response for valid otp :: "+ response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "generate_WithDTO", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OtpDTO> generateOTP_WithDTO(OtpDTO otpDto)
    {
    	LOGGER.info("Calling generateOTP_WithDTO...");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // check authentication
        if (username == null) {
        	LOGGER.info("Unauthorized access...");
            return new ResponseEntity<OtpDTO>(HttpStatus.UNAUTHORIZED);
        }

        // generate OTP.
        int otp = otpService.generateOtp(username);
        LOGGER.info("Actual otp {int} :: "+ otp);
        //Boolean isGenerated = otpService.generateOtp(username);
        if (otp < 0)
        {
        	otpDto.setStatus(OtpConstant.ERROR);
        	otpDto.setMessage(OtpConstant.ERROR_MESSAGE);
            LOGGER.info("response for invalid otp :: "+ otpDto.getMessage());
            return new ResponseEntity<OtpDTO>(HttpStatus.BAD_REQUEST);
        }

        // success message
        otpDto.setOtp(otp);
        otpDto.setStatus(OtpConstant.SUCCESS);
    	otpDto.setMessage(OtpConstant.SUCCESS_MESSAGE);
        LOGGER.info("response for valid otp :: "+ otpDto.getMessage());
        return new ResponseEntity<OtpDTO>(HttpStatus.OK);
    }
    
    @PostMapping(value = "validate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> validateOTP(@RequestBody Map<String, Object> otp)
    {
    	LOGGER.info("Calling validateOTP ... ");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Map<String, Object> response = new HashMap<>(2);

        // check authentication
        if (username == null) {
        	LOGGER.info("Unauthorized access to validateOTP ... ");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // validate provided OTP.
        Boolean isValid = otpService.validateOTP(username, (Integer) otp.get("otp"));
        LOGGER.info("Otp isValid : " + isValid);
        if (!isValid)
        {
            response.put("status", "error");
            response.put("message", "OTP is not valid!");
            LOGGER.info("Invalid otp response : " + response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // success message
        response.put("status", "success");
        response.put("message", "Entered OTP is valid!");
        LOGGER.info("Valid otp response : " + response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}