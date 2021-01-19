/*package com.pcr.service.controller;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.pcr.controller.OTPResourceController;
import com.pcr.service.OtpGenerator;
import com.pcr.service.OtpResourceService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = OTPResourceController.class)
@WithMockUser("admin")
public class OTPResourceControllerTest_WithMock {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private OtpResourceService otpResourceService;
	
	@MockBean
	private OtpGenerator otpGenerator;
	
	@Test
	public void testGenerateOtp_mock1() throws Exception {
		Random random = new Random();	
		int actualOtp = 100000 + random.nextInt(900000);
		Mockito.when(
				otpResourceService.generateOtp(Integer.toString(otpGenerator.generateOTP("admin")))).thenReturn(actualOtp);
				//otpResourceService.generateOtp(Mockito.anyString())).thenReturn(Mockito.anyInt());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/api/otp/generate").accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("response ::: " + result.getResponse().getContentAsString());
		String expected = "{\"message\":\"OTP successfully generated\",\"otp\":0,\"status\":\"success\"}";
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	public void testgenerateOTP_WithDTO() throws Exception {
		Random random = new Random();	
		int actualOtp = 100000 + random.nextInt(900000);
		Mockito.when(
				otpResourceService.generateOtp(Integer.toString(otpGenerator.generateOTP(Mockito.anyString())))).thenReturn(actualOtp);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/api/otp/generate_WithDTO").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		Map<String, String> response = new HashMap<>(2);
		response.put("otp", Integer.toString(otpResourceService.generateOtp(Mockito.anyString())));
        response.put("status", "success");
        response.put("message", "OTP successfully generated: "+otpResourceService.generateOtp(Mockito.anyString()));
		String expected = "{message=OTP successfully generated: 0, otp=0, status=success}";
				//"{id:Course1,name:Spring,description:10Steps}";

		// {"id":"Course1","name":"Spring","description":"10 Steps, 25 Examples and 10K Students","steps":["Learn Maven","Import Project","First Example","Second Example"]}
		//assertEquals(response, result);
		assertEquals(expected, result.getResponse()
				.getContentAsString());
	}
}
*/