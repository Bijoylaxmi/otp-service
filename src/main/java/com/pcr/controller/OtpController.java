package com.pcr.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pcr.exception.LimitExceededException;
import com.pcr.service.OtpAttemptService;
import com.pcr.service.OtpService;



/**
 * @author 513365
 * Oct 7, 2020
 */

@RestController
@RequestMapping("/otpService")
public class OtpController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public OtpService otpService;
	
	@Autowired
	public OtpAttemptService otpAttemptService;
	
	
	 @PostMapping(value = "generateOtp", produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Object> generateOTP()
	    {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String username = authentication.getName();

	        Map<String, String> response = new HashMap<>(2);

	        // check authentication
	        if (username == null) {
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	        }

	        // generate OTP.
	        int otp = otpService.generateOTP(username);
	        String otpStr = Integer.toString(otp);
	        if (otp < 0)
	        {
	            response.put("status", "error");
	            response.put("message", "OTP can not be generated.");
	            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	        }

	        // success message for valid otp generated
	        response.put("otp", otpStr);
	        response.put("status", "success");
	        response.put("message", "OTP successfully generated.");

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	 
	@PostMapping(value = "validateOtp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> validateOTP(@RequestBody Map<String, Object> otp)
    {
		final String SUCCESS = "Entered Otp is valid";
		final String FAIL = "Entered Otp is NOT valid. Please Retry!";
		final String LIMIT_EXCEEDED = "Maximum number of attempts exceeded for invalid otp!!!";
		final int MAX_ATTEMPT = 3;
		 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Map<String, String> response = new HashMap<>(2);

        // check authentication
        if (username == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // validate provided OTP.
        Boolean isValid = otpService.validateOTP(username, (Integer) otp.get("otp"));
		//int maxTry =3;
		int otpAttempt = 0;
		Boolean checkBlock = null;
        if (!isValid)
        {
        	otpAttempt = otpAttemptService.otpFailed(username);
			logger.info("otpAttempt :::"+ otpAttempt);
			checkBlock = otpAttemptService.isBlocked(username);
			logger.info("checkBlock :::"+ checkBlock);
			if(otpAttempt == 1) {
				logger.info("1st invalid attempt..");
				 response.put("status", "error");
		         response.put("message", FAIL);
		         return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			else if(otpAttempt == 2) {
				logger.info("2nd invalid attempt..");
				 response.put("status", "error");
		         response.put("message",FAIL);
		         return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			else if(otpAttempt == 3) {
				logger.info("3rd invalid attempt..");
				 response.put("status", "error");
		         response.put("message", FAIL);
		         return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			else if(otpAttempt > MAX_ATTEMPT && checkBlock == true) {
					logger.info("Invalid Otp retires exceeded ::: "+ otpAttempt+ " :: " +checkBlock);
					//otpAttemptService.otpExceeded(username);
					otpService.clearOTP(username);
					 response.put("status", "error");
			         response.put("message", LIMIT_EXCEEDED);
			         throw new LimitExceededException(LIMIT_EXCEEDED);
			        // return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        logger.info("Valid Otp...");
        // success message for valid otp
        response.put("status", "success");
        response.put("message", SUCCESS);
        otpAttemptService.otpExceeded(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	 
	
//	@GetMapping("/generateOtp1")
//	public Integer generateOtp1(){
//		
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
//		logger.info("auth from context ::"+ auth);
//		String username = auth.getName();
//		logger.info("username from context ::"+ username);
//		int otp = otpService.generateOTP(username);
//		logger.info("OTP : "+otp);
//		return otp;
//	}
	
	 
//	@RequestMapping(value ="/validateOtp1", method = RequestMethod.GET)
//	public @ResponseBody String validateOtp1(@RequestParam("otpnum") int otpnum) throws ExecutionException{
//		
//		final String SUCCESS = "Entered Otp is valid";
//		
//		final String FAIL = "Entered Otp is NOT valid. Please Retry!";
//		final String LIMIT_EXCEEDED = "Maximum number of attempts exceeded for wrong otp";
//		
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
//		String username = auth.getName();
//		
//		logger.info(" Otp Number : "+otpnum);
//		
//		//Validate the Otp 
////		if(otpnum >= 0){
////			int serverOtp = otpService.getOtp(username);
////			int otpAttempt = otpService.getOtpAttempt(username);
////			Boolean checkBlock = otpService.isBlocked(username);
////			if(serverOtp > 0){
////				if(otpnum == serverOtp){
////					otpService.clearOTP(username);
////					return ("Entered Otp is valid");
////				}else{
////					return SUCCESS;	
////				}
////			}else {
////				return FAIL;			
////			}
////		}else {
////			return FAIL;	
////		}
//		
//		//////
//		int attempts = 0;
//		int maxTry =3;
//		int otpAttempt = 0;
//		Boolean checkBlock = null;
//		if(otpnum >= 0){
//			int serverOtp = otpService.getOtp(username);
//			logger.info("serverOtp :::"+ serverOtp);
//			if(serverOtp > 0){
//				if(otpnum == serverOtp){
//					otpService.clearOTP(username);
//					return ("Entered Otp is valid");
//				}else{
//					logger.info("in 1st else");
//					return SUCCESS;	
//				}
//			}else {
//				logger.info("in 2nd else");
//				otpAttempt = otpAttemptService.otpFailed(username);
//				logger.info("otpAttempt :::"+ otpAttempt);
//				checkBlock = otpAttemptService.isBlocked(username);
//				logger.info("checkBlock :::"+ checkBlock);
//				if(otpAttempt == 1) {
//					logger.info("1st invalid attempt..");
//					return FAIL;
//				}
//				else if(otpAttempt == 2) {
//					logger.info("2nd invalid attempt..");
//					return FAIL;
//				}
//				else if(otpAttempt == 3) {
//					logger.info("3rd invalid attempt..");
//					return FAIL;
//				}
//				else if(otpAttempt > maxTry) {
//						logger.info("in else if otp limit exceeded ::: "+ otpAttempt+ checkBlock);
//						//otpAttemptService.otpExceeded(username);
//						return LIMIT_EXCEEDED;
//				}
//
//				while(checkBlock == true) {		
//					logger.info("in while ...."+ checkBlock);
//					break;
//				}
//				logger.info("out of if ... ");
//				return LIMIT_EXCEEDED;			
//			}
//		}else {
//			logger.info("in 3rd else");
//			return FAIL;	
//		}
//	}
	
//	@RequestMapping(value ="/validateOtp11", method = RequestMethod.GET)
//	public @ResponseBody String validateOtp11(@RequestParam("otpnum") int otpnum){
//		
//		final String SUCCESS = "Entered Otp is valid";
//		
//		final String FAIL = "Entered Otp is NOT valid. Please Retry!";
//		
//		final String AttemptExceeded = "Maximum number of attempts exceeded for wrong otp";
//		
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
//		String username = auth.getName();
//		
//		logger.info(" Otp Number : "+otpnum);
//		
//		/******* Validate the Otp  ************/
//		
//		if(otpnum >= 0){
//			int serverOtp = otpService.getOtp(username);
//			
//			if(serverOtp > 0){
//				if(otpnum == serverOtp){
//					otpService.clearOTP(username);
//					return ("Entered Otp is valid");
//				}else{
//					return SUCCESS;	
//				}
//			}else {
//				return FAIL;			
//			}
//		}else {
//			return FAIL;	
//		}
//		
//	}
	
}
